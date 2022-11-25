const tabla = document.querySelector("#tabla");

console.log("hola");

function insertarOdontologo(odontologos) {
    
    tabla.innerHTML = "";

    odontologos.forEach(odontologo => {
        tabla.innerHTML += `
        <tr>
            <th scope="row">${odontologo.id}</th>
            <td>${odontologo.numeroMatricula}</td>
            <td>${odontologo.nombre}</td>
            <td>${odontologo.apellido}</td>
            <td><button type="button" class="btn btn-danger" id="${odontologo.id}">Eliminar</button></td>
            <td><button type="button" class="btn btn-primary" id="${odontologo.id}">Modificar</button></td>
        </tr>
        `;

    })    
    
    const btnPut = document.querySelectorAll(".btn-primary");
    const btnDelete = document.querySelectorAll(".btn-danger");
    
    btnDelete.forEach(btn => {
        btn.addEventListener("click", function(e) {
            const swalWithBootstrapButtons = Swal.mixin({
                customClass: {
                  confirmButton: 'btn btn-success',
                  cancelButton: 'btn btn-danger'
                },
                buttonsStyling: false
              })
              
              swalWithBootstrapButtons.fire({
                title: 'Estas seguro?',
                text: "No podras volver atrás!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Si, borrar!',
                cancelButtonText: 'No, cancelar!',
                reverseButtons: true
              }).then((result) => {
                if (result.isConfirmed) {
                  swalWithBootstrapButtons.fire(
                    'Eliminado!',
                    'El odontologo ha sido eliminado.',
                    'success'
                  )
                  
                  setTimeout(() => {
                    borrarOdontologo(e.target.id)
                    location.reload();
                  }, 2000);
                  
                } else if (
                  result.dismiss === Swal.DismissReason.cancel
                ) {
                  swalWithBootstrapButtons.fire(
                    'Acción cancelada',
                    'El odotnologo esta a salvo :)',
                    'error'
                  )
                }
              })
        })
    })

    btnPut.forEach(element => {
        element.addEventListener("click", (e) => {
            Swal.fire({
                title: 'Modificar odontologo',
                html: `
                <label for="matricula">Matricula</label>
                <input type="text" id="matricula" class="swal2-input" placeholder="Matricula">
                <label for="nombre">Nombre</label>
                <input type="text" id="nombre" class="swal2-input" placeholder="Nombre">
                <label for="apellido">Apellido</label>
                <input type="text" id="apellido" class="swal2-input" placeholder="Apellido">
                `,
                confirmButtonText: 'Modificar',
                focusConfirm: false,
                preConfirm: () => {
                    const matricula = Swal.getPopup().querySelector('#matricula').value
                    const nombre = Swal.getPopup().querySelector('#nombre').value
                    const apellido = Swal.getPopup().querySelector('#apellido').value
                    if (!matricula || !nombre || !apellido) {
                        Swal.showValidationMessage(`Alguno de los campos esta vacio`)
                    }
                    return { id: e.target.id, numeroMatricula: matricula, nombre: nombre, apellido: apellido }
                }
              }).then((result) => {
                Swal.fire(`
                    Odontologo modificado!
                    Matricula: ${result.value.numeroMatricula}
                    Nombre: ${result.value.nombre}
                    Apellido: ${result.value.apellido}
                `.trim())
                modificarOdontologo(result.value)
              })

        })
    })


}

function consultarOdontologos() {
    fetch("/odontologos")
    .then(response => response.json())
    .then(data => {
        insertarOdontologo(data);    
    })
}

consultarOdontologos()

function borrarOdontologo(id) {
    const url = `/odontologos/${id}`;
    const config = {
        method: "DELETE"
    }  

    fetch(url, config)
    .then(res => res.json())
    .then(consultarOdontologos())
}

function modificarOdontologo(odontologo) {
    const url = `/odontologos`;
    const config = {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(odontologo)
    }  

    fetch(url, config)
    .then(res => res.json())
    .then(consultarOdontologos())
}