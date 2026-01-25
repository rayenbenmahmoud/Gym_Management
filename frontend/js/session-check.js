/**
 * Vérifie si l'utilisateur est déjà connecté.
 * Si oui, redirige vers le dashboard.
 */
async function checkSession() {

    const response = await fetch(`/api/dashboard`, {
        method: "GET",
        credentials: "include"
    });

    if (response.ok) {
        window.location.href = "dashboard.html";
    }
}

checkSession();
