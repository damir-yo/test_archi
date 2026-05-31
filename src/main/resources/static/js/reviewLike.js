document.addEventListener("DOMContentLoaded", () => {

    const likeButton = document.getElementById("like-button");
    const likesNumber = document.getElementById("likes-number");
    const csrfToken =
        document.querySelector('meta[name="_csrf"]').content;

    const csrfHeader =
        document.querySelector('meta[name="_csrf_header"]').content;
    if (!likeButton || !likesNumber) {
        return;
    }

    likeButton.addEventListener("click", async (event) => {

        event.preventDefault();

        const albumId = likeButton.dataset.albumId;
        const reviewId = likeButton.dataset.reviewId;

        const liked = likeButton.dataset.liked === "true";

        const url = liked
            ? `/albums/${albumId}/reviews/${reviewId}/likes/ajax/delete`
            : `/albums/${albumId}/reviews/${reviewId}/likes/ajax`;

        try {

            likeButton.disabled = true;

            const response = await fetch(url, {
                method: "POST",
                headers: {
                    [csrfHeader]: csrfToken
                }
            });

            if (!response.ok) {
                throw new Error("Request failed");
            }

            const data = await response.json();

            likesNumber.textContent = data.likesCount;

            likeButton.dataset.liked = data.liked;

            if (data.liked) {

                likeButton.textContent = "✓ Нравится";
                likeButton.style.background = "#2c2c2c";
                likeButton.style.border = "1px solid #404040";

            } else {

                likeButton.textContent = "❤️ Нравится";
                likeButton.style.background = "#ff9f6b";
                likeButton.style.border = "none";
            }

        } catch (error) {

            console.error("Like request failed:", error);

        } finally {

            likeButton.disabled = false;
        }
    });
});