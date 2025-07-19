 const form = document.getElementById('studentForm');
  form.addEventListener('submit', function (e) {
    e.preventDefault();

    const formData = new FormData(form);
    const data = {};

    formData.forEach((value, key) => {
      data[key] = value;
    });

    fetch('http://localhost:8080/api/students', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Basic ' + btoa('michael:qwerty') // ← логин и пароль сюда
      },
      body: JSON.stringify(data)
    })
      .then(res => {
        if (!res.ok) throw new Error('Request failed: ' + res.status);
        return res.json();
      })
      .then(result => {
        document.getElementById('response').textContent = 'Student added with ID: ' + result.id;
        form.reset();
      })
      .catch(err => {
        console.error(err);
        document.getElementById('response').textContent = 'Error adding student: ' + err.message;
      });
  });




