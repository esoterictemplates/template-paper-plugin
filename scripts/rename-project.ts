import { readFileSync, writeFileSync } from "fs";

const commandLineArguments = process.argv.slice(2);

/**
 * The top level domain of the new project, such as `net`, `dev`, `org`, `com`, etc. as provided by the person running the command.
 * 
 * The top level domain is provided by running this command:
 * `npm run rename-project -- new-project-top-level-domain new-project-author-name new-project-name`
 * 
 * Where:
 * - `new-project-top-level-domain` is replaced with the desired plugin top level domain, such as `net`, `dev`, `org`, `com`, etc.
 * - `new-project-author-name` is replaced with the desired plugin author name.
 * - `new-project-name` is replaced with the desired plugin name, in [kebab-case](https://developer.mozilla.org/en-US/docs/Glossary/Kebab_case) format.
 */
const newProjectTopLevelDomain = commandLineArguments[0];

/**
 * The name of the new project author, as specified by the person running the command.
 * 
 * This name is the same as it is in natural language.
 * 
 * The author name is provided by running this command:
 * `npm run rename-project -- new-project-top-level-domain new-project-author-name new-project-name`
 * 
 * Where:
 * - `new-project-top-level-domain` is replaced with the desired plugin top level domain, such as `net`, `dev`, `org`, `com`, etc.
 * - `new-project-author-name` is replaced with the desired plugin author name.
 * - `new-project-name` is replaced with the desired plugin name, in [kebab-case](https://developer.mozilla.org/en-US/docs/Glossary/Kebab_case) format.

 */
const newProjectAuthorName = commandLineArguments[1];

/**
 * The new name of the project, as provided by the person running the command.
 * This name is in [kebab-case](https://developer.mozilla.org/en-US/docs/Glossary/Kebab_case) format.
 * 
 * The name is provided by running this command:
 * `npm run rename-project -- new-project-top-level-domain new-project-author-name new-project-name`
 * 
 * Where:
 * - `new-project-top-level-domain` is replaced with the desired plugin top level domain, such as `net`, `dev`, `org`, `com`, etc.
 * - `new-project-author-name` is replaced with the desired plugin author name.
 * - `new-project-name` is replaced with the desired plugin name, in [kebab-case](https://developer.mozilla.org/en-US/docs/Glossary/Kebab_case) format.
**/
const newProjectName = commandLineArguments[2];

if (!newProjectName) {
    console.error("A project name must be specified");
    console.error("Please use the following command:");
    console.error("npm run rename-project -- new-project-name");
    console.error("Where 'new-project-name' is replaced with your desired plugin name, in kebab-case format.");
    console.error("Learn about kebab-case: https://developer.mozilla.org/en-US/docs/Glossary/Kebab_case");
    process.exit(1);
}

// Constants
const oldProjectTopLevelDomain = "net";
const oldProjectAuthorName = "Esoteric Slime";
const oldProjectName = "template-paper-plugin";

// File paths
const gradleSettingsFilePath = "settings.gradle.kts";

// Replace project name in the gradle settings
const gradleSettingsFileContent = readFileSync(gradleSettingsFilePath).toString();
writeFileSync(gradleSettingsFilePath, gradleSettingsFileContent.replace(`rootProject.name = "${oldProjectName}"`, newProjectName));
