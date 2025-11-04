// owner panel logic (approve/reject)
document.addEventListener('DOMContentLoaded', () => {
    const tableBody = document.querySelector('#requestsTable tbody');
    const adminKeyInput = document.getElementById('adminKey');
  
    const renderRow = (r) => {
      const date = r.createdAt ? new Date(r.createdAt).toLocaleString() : '';
      const extra = (r.actionBy || r.actionAt) ? ` ${r.actionBy ? 'by '+r.actionBy : ''} ${r.actionAt ? 'at '+new Date(r.actionAt).toLocaleString() : ''}` : '';
      const statusSpan = `<span class="${r.status === 'APPROVED' ? 'text-success' : r.status === 'REJECTED' ? 'text-danger' : 'text-warning'}">
        ${r.status} ${extra}
      </span>`;
  
      return `<tr>
        <td>${r.adopterName || (r.adopter ? r.adopter.name : '—')}</td>
        <td>${r.petName || (r.pet ? r.pet.name : '—')}</td>
        <td>${statusSpan}</td>
        <td>${date}</td>
        <td>
          ${r.status === 'PENDING' ? `<button class="btn btn-sm btn-success me-1 btn-approve" data-id="${r.id}">Approve</button><button class="btn btn-sm btn-warning btn-reject" data-id="${r.id}">Reject</button>` : ''}
        </td>
      </tr>`;
    };
  
    const loadRequests = async () => {
      const res = await API.listRequests();
      const list = res.data || [];
      tableBody.innerHTML = list.map(renderRow).join('');
  
      document.querySelectorAll('.btn-approve').forEach(btn => btn.addEventListener('click', async (e) => {
        const id = e.target.dataset.id;
        const key = adminKeyInput.value || prompt('Enter admin key to approve:');
        if (!key) return;
        const res = await API.approve(id, key);
        alert(res.message || (res.success ? 'Approved' : 'Error'));
        loadRequests();
      }));
  
      document.querySelectorAll('.btn-reject').forEach(btn => btn.addEventListener('click', async (e) => {
        const id = e.target.dataset.id;
        const key = adminKeyInput.value || prompt('Enter admin key to reject:');
        if (!key) return;
        const res = await API.reject(id, key);
        alert(res.message || (res.success ? 'Rejected' : 'Error'));
        loadRequests();
      }));
    };
  
    document.getElementById('refreshRequests').addEventListener('click', loadRequests);
    loadRequests();
  });
  