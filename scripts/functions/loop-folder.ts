import { readdirSync, readFileSync } from "fs"

export const loopFolder = (folderPath: string, callback: (filePath: string, fileContent: Buffer) => void) => {
    try {
        const folderFiles = readdirSync(folderPath);

        for (const file of folderFiles) {
            loopFolder(file, callback);
        }
    } catch (error) {
        callback(folderPath, readFileSync(folderPath));
        return;
    }
}
