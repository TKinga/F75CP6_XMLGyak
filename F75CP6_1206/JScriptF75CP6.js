
const fs = require("fs");

fs.readFile("./orarendF75CP6.json", "utf8", (error, data) => {
    if (error) {
        console.log(error);
        return;
    }
    console.log(JSON.stringify(JSON.parse(data), null, 2));
});