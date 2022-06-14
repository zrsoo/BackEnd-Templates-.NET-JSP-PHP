function addDocument() {
    $.post("DocumentController",
        {
            documentId: $("#documentId").val(),
            documentName: $("#documentName").val(),
            documentContents: $("#documentContents").val()
        })
}

function getDocumentsByUser(userId, callbackFunction) {
    $.getJSON("DocumentController",
        {
            action: "getDocuments",
            userId: userId
        },
        callbackFunction);
}

function getMostPopularDocument(callbackFunction) {
    $.getJSON("DocumentController",
        {
            action: "getMostPopularDocument"
        },
        callbackFunction)
}