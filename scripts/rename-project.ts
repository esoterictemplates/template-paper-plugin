const commandLineArguments = process.argv.slice(2);

/**
 * The new name of the project, as provided by the person running the command.
 * This name is in [kebab-case](https://developer.mozilla.org/en-US/docs/Glossary/Kebab_case) format.
 * 
 * The name is provided by running this command:
 * `npm run rename-project -- new-project-name`
 * 
 * Where `new-project-name` is replaced with the desired plugin name, in [kebab-case](https://developer.mozilla.org/en-US/docs/Glossary/Kebab_case) format. 
**/
const newProjectName = commandLineArguments[0]; // npm run rename-project -- new-project-name

if (!newProjectName) {
    console.error("A project name must be specified");
    console.error("Please use the following command:");
    console.error("npm run rename-project -- new-project-name");
    console.error("Where 'new-project-name' is replaced with your desired plugin name, in kebab-case format.");
    console.error("Learn about kebab-case: https://developer.mozilla.org/en-US/docs/Glossary/Kebab_case");
    process.exit(1);
}
