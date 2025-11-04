// centralized API functions — adjust base URL if needed
const API = {
    listPets: () => fetch('/api/pets').then(r => r.json()).then(result => result.data || []),
    addPet: (p) => fetch('/api/pets', {method:'POST', headers:{'Content-Type':'application/json'}, body:JSON.stringify(p)}).then(r=>r.json()),
    deletePet: (id) => fetch('/api/pets/'+id, {method:'DELETE'}).then(r=>r.json()),

    listAdopters: () => fetch('/api/adopters').then(r=>r.json()).then(result => result.data || []),
    createAdopter: (a) => fetch('/api/adopters', {method:'POST', headers:{'Content-Type':'application/json'}, body:JSON.stringify(a)}).then(r=>r.json()),

    listRequests: () => fetch('/api/requests').then(r=>r.json()).then(result => result.data || []),
    submitRequest: (petId, adopter) => fetch('/api/requests?petId='+petId, {method:'POST', headers:{'Content-Type':'application/json'}, body:JSON.stringify(adopter)}).then(r=>r.json()),
    approve: (id, key) => fetch('/api/requests/approve/'+id, {method:'POST', headers:{'X-ADMIN-KEY': key}}).then(r=>r.json()),
    reject: (id, key) => fetch('/api/requests/reject/'+id, {method:'POST', headers:{'X-ADMIN-KEY': key}}).then(r=>r.json())
  };
