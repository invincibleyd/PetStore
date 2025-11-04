// pet page logic
document.addEventListener('DOMContentLoaded', () => {
    const petsTableBody = document.querySelector('#petsTable tbody');
    const loadPets = async () => {
      const res = await API.listPets();
      const pets = res.data || [];
      petsTableBody.innerHTML = '';
      pets.forEach(p => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
          <td>${p.name}</td>
          <td>${p.type}</td>
          <td>${p.age}</td>
          <td>${p.available ? '✅' : '❌'}</td>
          <td>
            <button class="btn btn-sm btn-outline-primary btn-favorite" data-id="${p.id}">❤️ Favorite</button>
            <button class="btn btn-sm btn-danger btn-delete" data-id="${p.id}">Delete</button>
          </td>`;
        petsTableBody.appendChild(tr);
      });
  
      document.querySelectorAll('.btn-delete').forEach(b => b.addEventListener('click', async (ev) => {
        const id = ev.target.dataset.id;
        await API.deletePet(id);
        await loadPets();
      }));

      document.querySelectorAll('.btn-favorite').forEach(b => b.addEventListener('click', async (ev) => {
        const petId = ev.target.dataset.id;
        // Assuming user is logged in and we can get their ID
        const user = JSON.parse(localStorage.getItem('user'));
        if (!user || !user.id) {
          alert('Please log in to add favorites');
          return;
        }
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
      }));

    };
  
    document.getElementById('addPetBtn').addEventListener('click', async () => {
      const name = document.getElementById('petName').value.trim();
      const type = document.getElementById('petType').value.trim();
      const age = parseInt(document.getElementById('petAge').value, 10);
      if (!name || !type || isNaN(age)) return alert('Fill fields');
      await API.addPet({name, type, age});
      document.getElementById('petName').value='';
      document.getElementById('petType').value='';
      document.getElementById('petAge').value='';
      await loadPets();
    });
  
    loadPets();
  });
