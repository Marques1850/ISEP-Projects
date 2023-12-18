function generateTable(maxRows, maxColumns, postIts) {
    // Remove any existing table from the page
    const existingTable = document.querySelector("table");
    if (existingTable) {
        existingTable.remove();
    }
    let i;
    // Create the table element
    const table = document.createElement("table");
    // Create the table headers
    const thead = document.createElement("thead");
    const headerRow = document.createElement("tr");
    const empty = document.createElement("th");
    empty.classList.add("rounded-top-left");
    headerRow.appendChild(empty);
    for (i = 1; i <= maxColumns; i++) {
        const th = document.createElement("th");
        th.textContent = `Column ${i}`;
        if (i === maxColumns) {
            th.classList.add("rounded-top-right");
        }
        headerRow.appendChild(th);
    }
    thead.appendChild(headerRow);
    table.appendChild(thead);

    // Create the table rows
    const tbody = document.createElement("tbody");
    for (i = 1; i <= maxRows; i++) {
        const row = document.createElement("tr");
        const rowHeader = document.createElement("th");
        rowHeader.textContent = `Row ${i}`;
        if (i === maxRows) {
            rowHeader.classList.add("rounded-bottom-left");
        }
        row.appendChild(rowHeader);
        for (let j = 1; j <= maxColumns; j++) {
            const cell = document.createElement("td");
            cell.id = `post-${i}-${j}`;

            if (postIts.has(`${i},${j}`)) {
                cell.setAttribute('data-content', postIts.get(`${i},${j}`));
                cell.style.backgroundColor = '#F4D03F';
            }

            row.appendChild(cell);
            if (i === maxRows && j === maxColumns) {
                cell.classList.add("rounded-bottom-right");
            }
        }
        tbody.appendChild(row);
    }
    table.appendChild(tbody);

    // Append the table to the container
    document.body.appendChild(table);

    // Add event listeners to td elements
    const tdElements = document.querySelectorAll('td');
    tdElements.forEach(td => {
        td.addEventListener('click', () => {
            window.alert(td.getAttribute('data-content'));
        });
    });
}

function CallGenerateTable() {
    let pageurl = window.location.href;
    let pageurlsplit = pageurl.charAt(pageurl.length-1);
    let final = "/board".concat(pageurlsplit);
    var request = new XMLHttpRequest();
    console.log("CallGenerateTable");
    request.open("GET", final, true);
    request.send(); // Send the request to the server
    request.onload = function() {
        let boardString = request.responseText;

        let boardName = boardString.split(";")[0]; // Extract the board name from the response
        let maxRows = parseInt(boardString.split(";")[1]);
        let maxColumns = parseInt(boardString.split(";")[2]);

        let postIts = boardString.split(";")[3];
        let postItsArray = postIts.split("|");
        let postItsMap = new Map();
        for (let i = 0; i < postItsArray.length; i++) {
            let postIt = postItsArray[i].split(",");
            let row = parseInt(postIt[0]);
            let column = parseInt(postIt[1]);
            let content = postIt[2];
            postItsMap.set(`${row},${column}`, content);
        }

        // Set the text content of the h1 element with id "board-title" to the extracted board name
        document.querySelector("#board-title").textContent = boardName;

        // Pass the extracted values to the generateTable function
        generateTable(maxRows, maxColumns, postItsMap);

        // Hide the loading indicator and show the board title
        document.querySelector("#loading-indicator").style.display = "none";
        document.querySelector("#board-title").style.display = "block";

        // Call this function again after 2 seconds
        setTimeout(CallGenerateTable, 2000);
    };
}







