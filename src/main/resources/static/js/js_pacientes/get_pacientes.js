const tabla = document.querySelector("#tabla");

console.log("hola");

function insertarPacientes(pacientes) {
    
    tabla.innerHTML = "";

    let cantDomicilios = 1;

    pacientes.forEach(paciente => {
        const domicilio = `${paciente.domicilio.calle}, ${paciente.domicilio.numero}, ${paciente.domicilio.localidad}, ${paciente.domicilio.provinicia}`
        tabla.innerHTML += `
        <tr>
            <th scope="row">${paciente.id}</th>
            <td>${paciente.nombre}</td>
            <td>${paciente.apellido}</td>
            <td>${paciente.dni}</td>
            <td>${paciente.fechaIngreso}</td>
            <td>${paciente.mail}</td>
            <td>${domicilio}</td>
            <td><button type="button" class="btn btn-danger" id="${paciente.id}">Eliminar</button></td>
            <td><button type="button" class="btn btn-primary" id="${paciente.id}">Modificar</button></td>
        </tr>
        `;
        if (cantDomicilios < paciente.domicilio.id) {
            cantDomicilios++;
        }
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
                    let timerInterval
                    Swal.fire({
                      title: 'Eliminando paciente...',
                      html: 'El paciente se eliminara en <b></b> milisegundos.',
                      timer: 2000,
                      timerProgressBar: true,
                      didOpen: () => {
                        Swal.showLoading()
                        const b = Swal.getHtmlContainer().querySelector('b')
                        timerInterval = setInterval(() => {
                          b.textContent = Swal.getTimerLeft()
                        }, 100)
                      },
                      willClose: () => {
                        borrarPaciente(e.target.id)
                        location.reload()
                        clearInterval(timerInterval)
                      }
                    })   
                } else if (
                  result.dismiss === Swal.DismissReason.cancel
                ) {
                  swalWithBootstrapButtons.fire(
                    'Acción cancelada',
                    'El paciente esta a salvo :)',
                    'error'
                  )
                }
              })
        })
    })

    btnPut.forEach(element => {
        element.addEventListener("click", (e) => {
            idDomicilio(e.target.id, cantDomicilios);
        })
    })


}



function idDomicilio(id, cantDomicilios) {

    fetch("/pacientes/" + id)
    .then(response => response.json())
    .then(data => {
        Swal.fire({
            title: 'Modificar paciente',
            html: `
            <label for="nombre">Nombre</label>
            <input type="text" id="nombre" class="swal2-input" placeholder="Nombre">
            <br>
            <label for="apellido">Apellido</label>
            <input type="text" id="apellido" class="swal2-input" placeholder="Apellido">
            <br>
            <label for="dni">Dni</label>
            <input type="text" id="dni" class="swal2-input" placeholder="Dni">
            <br>
            <label for="fecha-ingreso">Fecha de ingreso</label>
            <input type="date" id="fecha-ingreso" class="swal2-input" placeholder="Fecha de ingreso">
            <br>
            <label for="mail">Mail</label>
            <input type="text" id="mail" class="swal2-input" placeholder="Mail">
            <br>
            <hr>
            <p>Esta parte es opcional. Dejar vacia en caso de querer conservar el domicilio.</p>
            <br>
            <label for="calle">Calle</label>
            <input type="text" id="calle" class="swal2-input" placeholder="Calle">
            <br> 
            <label for="numero">Numero</label>
            <input type="text" id="numero" class="swal2-input" placeholder="Numero">
            <br>
            <label for="localidad">Localidad</label>
            <input type="text" id="localidad" class="swal2-input" placeholder="Localidad">
            <br>
            <label for="provincia">Provincia</label>
            <input type="text" id="provincia" class="swal2-input" placeholder="Provincia">
            `,
            confirmButtonText: 'Modificar',
            focusConfirm: false,
            preConfirm: () => {
                const nombre = document.querySelector('#nombre').value;
                const apellido = document.querySelector('#apellido').value;
                const dni = document.querySelector('#dni').value;
                const fechaIngreso = document.querySelector('#fecha-ingreso').value;
                const mail = document.querySelector('#mail').value;
                const calle = document.querySelector('#calle').value;
                const numero = document.querySelector('#numero').value;
                const localidad = document.querySelector('#localidad').value;
                const provinicia = document.querySelector('#provincia').value; 
                
                if (!nombre || !apellido || !dni || !fechaIngreso || !mail) {
                    Swal.showValidationMessage(`Alguno de los campos esta vacio`)
                } else if (!calle || !numero || !localidad || !provinicia) {

                    return { 
                        id: id, 
                        nombre: nombre, 
                        apellido: apellido, 
                        dni: dni,
                        fechaIngreso: fechaIngreso,
                        domicilio: data.domicilio,
                        mail: mail 
                    }
                } else {
                    return { 
                        id: id, 
                        nombre: nombre, 
                        apellido: apellido, 
                        dni: dni,
                        fechaIngreso: fechaIngreso,
                        domicilio: {
                            id: cantDomicilios + 1,
                            calle: calle,
                            numero: Number(numero),
                            localidad: localidad,
                            provinicia: provinicia
                        },
                        mail: mail 
                    }
                }

                
            }
            }).then((result) => {

                let timerInterval
                Swal.fire({
                  title: 'Actualizando paciente...',
                  html: 'El paciente se actualizara en <b></b> milisegundos.',
                  timer: 2000,
                  timerProgressBar: true,
                  didOpen: () => {
                    Swal.showLoading()
                    const b = Swal.getHtmlContainer().querySelector('b')
                    timerInterval = setInterval(() => {
                      b.textContent = Swal.getTimerLeft()
                    }, 100)
                  },
                  willClose: () => {
                    modificarPaciente(result.value)
                    location.reload()
                    clearInterval(timerInterval)
                  }
                })              
          })
    })

}

function consultarPacientes() {

    fetch("/pacientes")
    .then(response => response.json())
    .then(data => {
        insertarPacientes(data);    
    })

}

consultarPacientes()

function borrarPaciente(id) {
    const url = `/pacientes/${id}`;
    const config = {
        method: "DELETE"
    }  

    fetch(url, config)
    .then(res => res.json())
    .then(consultarPacientes())
}

function modificarPaciente(paciente) {
    console.log(paciente);
    const url = `/pacientes`;
    const config = {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(paciente)
    }  

    fetch(url, config)
    .then(res => res.json())
    .then(consultarPacientes())
}