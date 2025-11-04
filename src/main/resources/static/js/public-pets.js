const API_BASE = 'http://localhost:8082/api';

document.addEventListener('DOMContentLoaded', function() {
    loadPublicPets();
});

async function loadPublicPets() {
    try {
        const response = await fetch(`${API_BASE}/pets?available=true`);
        const result = await response.json();

        // The API returns {success: true, message: "Available pets", data: [...]}
        const pets = result.data || [];

        const petsList = document.getElementById('publicPetsList');
        petsList.innerHTML = '';

        pets.forEach(pet => {
            const petCard = `
                <div class="col-md-4 mb-4">
                    <div class="pet-card">
                        <h5 class="fw-bold mb-2">${pet.name}</h5>
                        <p class="mb-1"><strong>Type:</strong> ${pet.type}</p>
                        <p class="mb-1"><strong>Age:</strong> ${pet.age} years</p>
                        ${pet.description ? `<p class="mb-2"><strong>Description:</strong> ${pet.description}</p>` : ''}
                        <p class="text-muted small">Register or login to adopt this pet!</p>
                    </div>
                </div>
            `;
            petsList.innerHTML += petCard;
        });

        if (pets.length === 0) {
            petsList.innerHTML = '<p class="text-center">No pets available for adoption at the moment.</p>';
        }
    } catch (error) {
        console.error('Error loading pets:', error);
        document.getElementById('publicPetsList').innerHTML = '<p class="text-center text-danger">Unable to load pets. Please try again later.</p>';
    }
}
