  const form = document.getElementById('studentForm');
  const responseEl = document.getElementById('response');

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
        'Authorization': 'Basic ' + btoa('michael:qwerty')
      },
      body: JSON.stringify(data)
    })
    .then(res => {
      if (!res.ok) throw new Error('Request failed: ' + res.status);
      return res.json();
    })
    .then(result => {
      responseEl.textContent = 'Message sent successfully!';
      responseEl.className = 'response-msg success';
      form.reset();

      setTimeout(() => {
        responseEl.textContent = '';
        responseEl.className = 'response-msg';
      }, 5000);
    })
    .catch(err => {
      console.error(err);
      responseEl.textContent = 'Error sending message: ' + err.message;
      responseEl.className = 'response-message error';

      setTimeout(() => {
        responseEl.textContent = '';
        responseEl.className = 'response-msg';
      }, 5000);
    });
  });