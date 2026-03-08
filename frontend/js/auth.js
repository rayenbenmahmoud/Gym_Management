/**
 * Cette méthode est exécutée lorsque l'utilisateur
 * soumet le formulaire de connexion.
 * Elle envoie les informations de connexion au backend
 * et gère la réponse (succès ou erreur).
 */
document.getElementById("loginForm").addEventListener("submit", login);

async function login(event) {

    // Empêche le rechargement de la page
    event.preventDefault();

    // Récupération des valeurs saisies par l'utilisateur
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    // Construction de l'objet envoyé au backend
    const loginData = {
        username: username,
        password: password
    };

    try {
        const response = await fetch(`/auth/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "include", // IMPORTANT pour la session
            body: JSON.stringify(loginData)
        });

        if (response.ok) {
            // Connexion réussie
            window.location.href = "dashboard.html";
        } else {
            // Identifiants invalides
            document.getElementById("errorMessage").innerText =
                "Nom d'utilisateur ou mot de passe incorrect";
        }

    } catch (error) {
        document.getElementById("errorMessage").innerText =
            "Erreur de connexion au serveur";
    }
}
