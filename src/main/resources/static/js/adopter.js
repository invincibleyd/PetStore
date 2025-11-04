// adopters page logic
document.addEventListener('DOMContentLoaded', () => {
    const adoptersDiv = document.getElementById('adoptersList');
  
    const renderAdopters = (list) => {
      adoptersDiv.innerHTML = list.map(a => `
        <div class="p-2 border rounded mb-2 bg-white">
          <b>${a.name}</b> (${a.email})<br>
          ID: ${a.adopterId || '—'}<br>
          IRS/PAN: ${a.irsId || '—'}<br>
          Status: ${a.verified ? '✅ Verified' : '❌ Pending'}
        </div>
      `).join('');
    };
  
    const loadAdopters = async () => {
      const list = await API.listAdopters();
      renderAdopters(list);
    };
  
    document.getElementById('registerAdopterBtn').addEventListener('click', async () => {
      const name = document.getElementById('adopterName').value.trim();
      const email = document.getElementById('adopterEmail').value.trim();
      const phone = document.getElementById('adopterPhone').value.trim();
      const address = document.getElementById('adopterAddress').value.trim();
      const irs = document.getElementById('adopterIrs').value.trim();
  
      if (!name || !email) return alert('Name and email required');
      const payload = { name, email, phone, address, irsId: irs };
      const res = await API.createAdopter(payload);
      if (res && res.success) {
        alert('Adopter registered');
        document.getElementById('adopterName').value='';
        document.getElementById('adopterEmail').value='';
        document.getElementById('adopterPhone').value='';
        document.getElementById('adopterAddress').value='';
        document.getElementById('adopterIrs').value='';
        loadAdopters();
      } else {
        alert(res.message || 'Error');
      }
    });
  
    document.getElementById('refreshAdopters').addEventListener('click', loadAdopters);
    loadAdopters();
  });
