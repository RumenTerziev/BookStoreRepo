window.addEventListener('load', attachEvents)

function attachEvents() {
    const BASE_URL = '/api/bookstore';
    const COMMENTS_URL = '/api/bookstore/comments';

    const allDomElements = {
        loadButton: document.getElementById('loadBooks'),
        table: document.getElementsByTagName('table')[0],
        tbody: document.getElementsByTagName('tbody')[0],
        form: document.getElementById('form'),
        searchInput: document.getElementById('search-input'),
        searchButton: document.getElementById('search-button')
    }
    const newDomElements = {
        formTitle: allDomElements.form.children[0],
        titleInput: allDomElements.form.children[2],
        authorInput: allDomElements.form.children[4],
        submitButton: allDomElements.form.children[5],
    }

    allDomElements.loadButton.addEventListener('click', loadHandler);
    allDomElements.searchButton.addEventListener('click', searchHandler);
    newDomElements.submitButton.addEventListener('click', createHandler);
    allDomElements.searchButton.setAttribute('disabled', 'true');

    function loadHandler(event) {

        if (event) {
            event.preventDefault();
        }

        allDomElements.searchButton.removeAttribute('disabled');

        fetch(BASE_URL)
            .then((resp) => resp.json())
            .then((data) => {

                allDomElements.tbody.innerHTML = '';

                for (const current of data) {
                    let {title, author, id} = current;
                    let tr = createTableRow(title, author);
                    tr.id = id;
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


    function saveHandler(event) {

        if (event) {
            event.preventDefault();
        }

        let payload = JSON.stringify({
            title: newDomElements.titleInput.value,
            author: newDomElements.authorInput.value,
            id: searchedId
        });

        let requestOptions = {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json"
            },
            body: payload
        };

        fetch(`${BASE_URL}/${searchedId}`, requestOptions)
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

        if (event) {
            event.preventDefault();
        }

        let searchedTr = this.parentNode.parentNode;
        let searchedId = searchedTr.id;

        let requestOptions = {
            method: "DELETE",
        };


        fetch(`${BASE_URL}/${searchedId}`, requestOptions)
            .then(() => {
                loadHandler();
            })
            .catch((err) => {
                console.error(err);
            });

    }


    function commentsHandler() {

        let tdForm = document.createElement('td');
        tdForm.id = "tdContainerForm";
        let form = document.createElement('form');
        let inputComment = document.createElement('input');
        inputComment.type = "text";


        let searchedTr = this.parentNode.parentNode;
        let id = searchedTr.id;

        let submitComment = document.createElement('button');
        submitComment.textContent = "Submit Comment";
        submitComment.addEventListener('click', submitCommentHandler);
        form.appendChild(inputComment);
        form.appendChild(submitComment);
        tdForm.appendChild(form);
        searchedTr.appendChild(tdForm);


        loadComments(id);

        this.setAttribute('disabled', true);

    }

    function submitCommentHandler(event) {

        if (event) {
            event.preventDefault();
        }

        let searchedTr = this.parentNode.parentNode.parentNode;
        let searchedId = searchedTr.id;
        let td = searchedTr.getElementsByTagName('td')[0];
        let name = td.textContent;
        let input = searchedTr.getElementsByTagName('td')[3].getElementsByTagName('input')[0];

        if (input.value === '') {
            return;
        }

        let payload = JSON.stringify({
            bookTitle: name,
            comment: input.value
        });


        let reqOptions = {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: payload
        }
        let lastTd = searchedTr.getElementsByTagName('td')[4];


        fetch(`${COMMENTS_URL}/${searchedId}`, reqOptions)
            .then(() => {
                lastTd.remove();
                loadComments(searchedId);
                input.value = '';
            })
            .catch((err) => {
                console.error(err);
            });
    }


    function loadComments(id, event) {
        if (event) {
            event.preventDefault();
        }
        let tdComments = document.createElement('td');
        let ul = document.createElement('ul');
        let span = document.createElement('span');

        let allTrs = Array.from(document.getElementsByTagName('tr'));
        let searchedTr = allTrs.find(tr => tr.id === id);


        let reqOptions = {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        }

        fetch(`${COMMENTS_URL}/${id}`, reqOptions)
            .then((resp) => resp.json())
            .then((data) => {

                if (data.length === 0) {
                    span.textContent = 'No comments to show!';
                    ul.appendChild(span);
                } else {

                    for (const current of data) {
                        let {bookId, comment, commentId} = current;
                        searchedTr.id = bookId;
                        let li = document.createElement('li');
                        li.textContent = comment;
                        li.id = commentId;
                        let button = document.createElement('button');
                        button.textContent = 'Delete';
                        button.addEventListener('click', deleteCommentHandler);
                        li.appendChild(button);
                        ul.appendChild(li);
                    }
                }
                tdComments.appendChild(ul);
                searchedTr.appendChild(tdComments);

            })
            .catch((error) => {
                console.error(error);
            });

    }

    function deleteCommentHandler() {

        let commentId = this.parentNode.id;
        let td = this.parentNode.parentNode.parentNode;
        let bookId = td.parentNode.id;

        let requestOptions = {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            }
        };


        fetch(`${COMMENTS_URL}/${commentId}`, requestOptions)
            .then(() => {
                td.remove();
                loadComments(bookId);
            })
            .catch((err) => {
                console.error(err);
            });

    }

    function searchHandler(event) {

        if (allDomElements.searchInput.value === '') {
            return;
        }

        let name = allDomElements.searchInput.value;

        if (event) {
            event.preventDefault();
        }

        const url = `/api/bookstore?title=${encodeURIComponent(name)}`;

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error(response.statusText);
                }
                return response.json();
            })
            .then(data => {
                if (data.length === 0) {
                    alert('No matches with searched title!');
                } else {
                    let array = [];
                    for (const current of data) {
                        let id = current.id;
                        let currentCopy = document.getElementById(id);
                        array.push(currentCopy);
                    }

                    allDomElements.tbody.innerHTML = '';
                    for (const currentTr of array) {
                        allDomElements.tbody.appendChild(currentTr);
                    }
                }
                allDomElements.searchInput.value = '';
            })
            .catch((error) => {

                allDomElements.searchInput.value = '';
                console.error(error);

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
        let commentsButton = document.createElement('button');
        commentsButton.textContent = 'Comments';
        commentsButton.addEventListener('click', commentsHandler);
        editButton.addEventListener('click', editHandler);
        deleteButton.addEventListener('click', deleteHandler);
        tdButtons.appendChild(editButton);
        tdButtons.appendChild(deleteButton);
        tdButtons.appendChild(commentsButton);
        tr.appendChild(tdTitle);
        tr.appendChild(tdAuthor);
        tr.appendChild(tdButtons);
        return tr;
    }
}