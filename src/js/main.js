  
// CONTACT FORM
  const form = document.getElementById('studentForm');
  form.addEventListener('submit', function (e) {
    e.preventDefault();

    const formData = new FormData(form);
    const data = {
      name: formData.get('fullname'),
      email: formData.get('email'),
      message: formData.get('message')
    };

    fetch('http://localhost:8080/api/contact', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Basic ' + btoa('michael:qwerty') // ← если нужно
      },
      body: JSON.stringify(data)
    })
    .then(res => {
      if (!res.ok) throw new Error('Request failed: ' + res.status);
      return res.json();
    })
    .then(result => {
      document.getElementById('response').textContent = 'Message sent successfully!';
      form.reset();
    })
    .catch(err => {
      console.error(err);
      document.getElementById('response').textContent = 'Error sending message: ' + err.message;
    });
  });
  // CONTACT FORM