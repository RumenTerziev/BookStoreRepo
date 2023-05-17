window.addEventListener('load', attachEvents)

function attachEvents() {
    const BASE_URL = '/api/bookstore';

    const allDomElements = {
        loadButton: document.getElementById('loadBooks'),
        tbody: document.getElementsByTagName('tbody')[0],
        form: document.getElementById('form')
    }
    const newDomElements = {
        formTitle: allDomElements.form.children[0],
        titleInput: allDomElements.form.children[2],
        authorInput: allDomElements.form.children[4],
        submitButton: allDomElements.form.children[5],
    }

    newDomElements.submitButton.addEventListener('click', createHandler);

    allDomElements.loadButton.addEventListener('click', loadHandler);

    function loadHandler(event) {

        if (event) {
            event.preventDefault();
        }

        fetch(BASE_URL)
            .then((resp) => resp.json())
            .then((data) => {

                allDomElements.tbody.innerHTML = '';

                for (const current of data) {
                    let {title, author} = current;
                    let tr = createTableRow(title, author);
                    tr.id = title;
                    allDomElements.tbody.appendChild(tr);
                }

            })
            .catch((err) => {
                console.error(err);
            });

    }


    function createHandler(event) {

        if (event) {
            event.preventDefault();
        }

        if (newDomElements.authorInput.value === '' || newDomElements.titleInput.value === '') {
            return;
        }


        let currentTitle = newDomElements.titleInput.value;
        let currentAuthor = newDomElements.authorInput.value;

        let payload = JSON.stringify({
            title: currentTitle,
            author: currentAuthor
        });

        let requestOptions = {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: payload
        };


        fetch(BASE_URL, requestOptions)
            .then((resp) => resp.json())
            .then(() => {
                loadHandler(event);
                newDomElements.authorInput.value = '';
                newDomElements.titleInput.value = '';
            })
            .catch((err) => {
                console.error(err);
            });

    }

    let searchedId;

    function editHandler(event) {

        if (event) {
            event.preventDefault();
        }

        let searchedTr = this.parentNode.parentNode;

        searchedId = searchedTr.id;
        newDomElements.formTitle.textContent = 'Edit FORM';


        let tds = Array.from(searchedTr.children);
        let firstTd = tds[0];
        let secondTd = tds[1];


        newDomElements.titleInput.value = firstTd.textContent;
        newDomElements.authorInput.value = secondTd.textContent;


        allDomElements.form.children[5].remove();

        let saveButton = document.createElement('button');
        saveButton.textContent = 'Save';
        saveButton.addEventListener('click', saveHandler);

        allDomElements.form.appendChild(saveButton);

    }


    function saveHandler() {

        let payload = JSON.stringify({
            title: newDomElements.titleInput.value,
            author: newDomElements.authorInput.value
        });

        let requestOptions = {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json"
            },
            body: payload
        };

        fetch(`${BASE_URL}/${searchedId}`, requestOptions)
            .then((resp) => resp.json())
            .then(() => {
                loadHandler();
                newDomElements.titleInput.value = '';
                newDomElements.authorInput.value = '';
            })
            .catch((err) => {
                console.error(err);
            });


        this.remove();
        let submitButton = document.createElement('button');
        submitButton.textContent = 'Submit';
        submitButton.addEventListener('click', createHandler);
        allDomElements.form.appendChild(submitButton);

    }


    function deleteHandler(event) {
        let searchedTr = this.parentNode.parentNode;
        let td = searchedTr.getElementsByTagName('td')[0];
        let name = td.textContent;

        let requestOptions = {
            method: "DELETE",
        };


        fetch(`${BASE_URL}/${name}`, requestOptions)
            .then((resp) => resp.json())
            .then(() => {
                loadHandler(event);
            })
            .catch((err) => {
                console.error(err);
            });


    }


    function createTableRow(title, author) {
        let tr = document.createElement('tr');
        let tdTitle = document.createElement('td');
        let tdAuthor = document.createElement('td');
        tdTitle.textContent = title;
        tdAuthor.textContent = author;
        let tdButtons = document.createElement('td');
        let editButton = document.createElement('button');
        let deleteButton = document.createElement('button');
        editButton.textContent = 'Edit';
        deleteButton.textContent = 'Delete';
        editButton.addEventListener('click', editHandler);
        deleteButton.addEventListener('click', deleteHandler);
        tdButtons.appendChild(editButton);
        tdButtons.appendChild(deleteButton);
        tr.appendChild(tdTitle);
        tr.appendChild(tdAuthor);
        tr.appendChild(tdButtons);
        return tr;
    }

}