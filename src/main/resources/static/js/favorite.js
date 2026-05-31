document.addEventListener("DOMContentLoaded", () => {

    const favoriteButton = document.getElementById("favorite-button");

    if (!favoriteButton) {
        return;
    }

    const csrfTokenMeta =
        document.querySelector('meta[name="_csrf"]');

    const csrfHeaderMeta =
        document.querySelector('meta[name="_csrf_header"]');

    if (!csrfTokenMeta || !csrfHeaderMeta) {

        console.error("CSRF-метатеги не найдены");

        return;
    }

    const csrfToken = csrfTokenMeta.content;
    const csrfHeader = csrfHeaderMeta.content;

    favoriteButton.addEventListener("click", async (event) => {

        event.preventDefault();

        const albumId =
            favoriteButton.dataset.albumId;

        const isFavorite =
            favoriteButton.dataset.favorite === "true";

        const url = isFavorite
            ? `/favorites/${albumId}/ajax/delete`
            : `/favorites/${albumId}/ajax`;

        try {

            favoriteButton.disabled = true;

            const response = await fetch(url, {
                method: "POST",
                headers: {
                    [csrfHeader]: csrfToken
                }
            });

            if (!response.ok) {

                throw new Error(
                    `Ошибка запроса: ${response.status}`
                );
            }

            const data = await response.json();

            favoriteButton.dataset.favorite =
                data.favorite;

            if (data.favorite) {

                favoriteButton.innerHTML =
                    "★ В избранном";

            } else {

                favoriteButton.innerHTML =
                    "☆ Добавить в избранное";
            }

        } catch (error) {

            console.error(
                "Ошибка при работе с избранным:",
                error
            );

        } finally {

            favoriteButton.disabled = false;
        }
    });
});