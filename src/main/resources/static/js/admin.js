document.addEventListener('DOMContentLoaded', function() {
    if (!isAdmin()) {
        window.location.href = 'login.html';
        return;
    }

    loadAdoptionRequests();
});

async function loadAdoptionRequests() {
    try {
        console.log('Loading adoption requests...');
        const response = await fetch(`${API_BASE}/requests`);
        console.log('Response status:', response.status);
        const result = await response.json();
        console.log('API response:', result);
        const requests = result.data || [];

        const requestsList = document.getElementById('requestsList');
        requestsList.innerHTML = '';

        if (requests.length === 0) {
            requestsList.innerHTML = '<p>No adoption requests.</p>';
            console.log('No requests found');
            return;
        }

        console.log('Rendering requests:', requests);
        requests.forEach(request => {
            const statusClass = request.status === 'APPROVED' ? 'text-success' :
                               request.status === 'REJECTED' ? 'text-danger' : 'text-warning';
            const requestItem = `
                <div class="mb-3 p-3 border rounded">
                    <p><strong>Request ID:</strong> ${request.id}</p>
                    <p><strong>Pet ID:</strong> ${request.petId}</p>
                    <p><strong>User ID:</strong> ${request.adopterId}</p>
                    <p><strong>Message:</strong> ${request.message || 'No message'}</p>
                    <p><strong>Status:</strong> <span class="${statusClass}">${request.status}</span></p>
                    <p><strong>Created:</strong> ${new Date(request.createdAt).toLocaleDateString()}</p>
                    ${request.status === 'PENDING' ? `
                        <button class="btn btn-success btn-sm me-2" onclick="approveRequest(${request.id})">Approve</button>
                        <button class="btn btn-danger btn-sm" onclick="rejectRequest(${request.id})">Reject</button>
                    ` : ''}
                    <br>
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

async function approveRequest(requestId) {
    try {
        console.log('Approving request:', requestId);
        const response = await fetch(`${API_BASE}/requests/approve/${requestId}`, {
            method: 'PUT'
        });
        console.log('Approve response status:', response.status);
        const result = await response.json();
        console.log('Approve response:', result);

        if (response.ok) {
            alert('Request approved successfully!');
            loadAdoptionRequests();
        } else {
            alert('Failed to approve request: ' + (result.message || 'Unknown error'));
        }
    } catch (error) {
        console.error('Error approving request:', error);
        alert('An error occurred: ' + error.message);
    }
}

async function rejectRequest(requestId) {
    try {
        console.log('Rejecting request:', requestId);
        const response = await fetch(`${API_BASE}/requests/reject/${requestId}`, {
            method: 'PUT'
        });
        console.log('Reject response status:', response.status);
        const result = await response.json();
        console.log('Reject response:', result);

        if (response.ok) {
            alert('Request rejected successfully!');
            loadAdoptionRequests();
        } else {
            alert('Failed to reject request: ' + (result.message || 'Unknown error'));
        }
    } catch (error) {
        console.error('Error rejecting request:', error);
        alert('An error occurred: ' + error.message);
    }
}
