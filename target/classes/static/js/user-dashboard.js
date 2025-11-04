// API_BASE is declared in auth.js

document.addEventListener('DOMContentLoaded', function() {
    const user = getCurrentUser();
    if (!user || !user.id) {
        window.location.href = 'login.html';
        return;
    }

    document.getElementById('userName').textContent = user.name || 'User';

    loadAvailablePets();
    loadUserRequests();

    document.getElementById('petForm').addEventListener('submit', handlePetRegistration);
});

function logout() {
    localStorage.removeItem('user');
    localStorage.removeItem('isAdmin');
    window.location.href = 'index.html';
}

function getCurrentUser() {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
}

function isAdmin() {
    return localStorage.getItem('isAdmin') === 'true';
}

async function loadAvailablePets() {
    try {
        console.log('Loading available pets...');
        const response = await fetch(`${API_BASE}/pets?available=true`);
        console.log('Response status:', response.status);
        const result = await response.json();
        console.log('API response:', result);
        const pets = result.data || []; // Extract the data array from the response

        const petsList = document.getElementById('petsList');
        petsList.innerHTML = '';

        if (!pets || pets.length === 0) {
            petsList.innerHTML = '<p>No available pets at the moment.</p>';
            console.log('No pets found');
            return;
        }

        console.log('Rendering pets:', pets);
        pets.forEach(pet => {
            const petCard = `
                <div class="col-md-6 mb-3">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">${pet.name}</h5>
                            <p class="card-text">Type: ${pet.type}</p>
                            <p class="card-text">Age: ${pet.age}</p>
                            <p class="card-text">${pet.description || ''}</p>
                            <div class="btn-group w-100" role="group">
                                <button class="btn btn-outline-primary" onclick="addToFavorites(${pet.id})">
                                    ❤️ Favorite
                                </button>
                                <button class="btn btn-primary" onclick="submitAdoptionRequest(${pet.id})">Adopt</button>
                            </div>
                        </div>
                    </div>
                </div>
            `;
            petsList.innerHTML += petCard;
        });
        console.log('Pets loaded successfully:', pets.length);
        console.log('petsList innerHTML:', petsList.innerHTML);
    } catch (error) {
        console.error('Error loading pets:', error);
        document.getElementById('petsList').innerHTML = '<p>Error loading pets. Please try again later.</p>';
    }
}

async function loadUserRequests() {
    const user = getCurrentUser();
    try {
        console.log('Loading user requests for user ID:', user.id);
        const response = await fetch(`${API_BASE}/requests/user/${user.id}`);
        console.log('Requests response status:', response.status);
        const result = await response.json();
        console.log('Requests API response:', result);
        const requests = result.data || []; // Extract the data array from the response

        const requestsList = document.getElementById('requestsList');
        requestsList.innerHTML = '';

        if (!requests || requests.length === 0) {
            requestsList.innerHTML = '<p>No adoption requests yet.</p>';
            console.log('No requests found');
            return;
        }

        console.log('Rendering requests:', requests);
        requests.forEach(request => {
            const statusClass = request.status === 'APPROVED' ? 'text-success' :
                               request.status === 'REJECTED' ? 'text-danger' : 'text-warning';
            const requestItem = `
                <div class="mb-3 p-3 border rounded">
                    <p><strong>Pet ID:</strong> ${request.petId}</p>
                    <p><strong>Message:</strong> ${request.message || 'No message'}</p>
                    <p><strong>Status:</strong> <span class="${statusClass}">${request.status}</span></p>
                    <p><strong>Created:</strong> ${new Date(request.createdAt).toLocaleDateString()}</p>
                </div>
            `;
            requestsList.innerHTML += requestItem;
        });
        console.log('Requests loaded successfully:', requests.length);
        console.log('requestsList innerHTML:', requestsList.innerHTML);
    } catch (error) {
        console.error('Error loading requests:', error);
        document.getElementById('requestsList').innerHTML = '<p>Error loading requests. Please try again later.</p>';
    }
}

async function submitAdoptionRequest(petId) {
    const user = getCurrentUser();
    const message = prompt('Enter a message for your adoption request:');

    if (!message) return;

    try {
        const response = await fetch(`${API_BASE}/requests`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                petId: petId,
                adopterId: user.id,
                message: message
            })
        });

        if (response.ok) {
            alert('Adoption request submitted successfully!');
            loadUserRequests();
        } else {
            alert('Failed to submit request');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred');
    }
}

async function addToFavorites(petId) {
    const user = getCurrentUser();
    try {
        const response = await fetch('/api/favorites', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                userId: user.id,
                petId: petId
            })
        });

        if (response.ok) {
            alert('Pet added to favorites!');
        } else {
            const result = await response.json();
            alert('Failed to add to favorites: ' + (result.message || 'Unknown error'));
        }
    } catch (error) {
        console.error('Error adding to favorites:', error);
        alert('An error occurred while adding to favorites');
    }
}

async function handlePetRegistration(e) {
    e.preventDefault();

    const user = getCurrentUser();
    const pet = {
        name: document.getElementById('petName').value,
        type: document.getElementById('petType').value,
        age: parseInt(document.getElementById('petAge').value),
        description: document.getElementById('petDescription').value,
        ownerId: user.id,
        available: true,
        active: true
    };

    try {
        const response = await fetch(`${API_BASE}/pets`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(pet)
        });

        if (response.ok) {
            alert('Pet registered successfully!');
            document.getElementById('petForm').reset();
            loadAvailablePets();
        } else {
            alert('Failed to register pet');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred');
    }
}
