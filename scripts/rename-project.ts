const commandLineArguments = process.argv.slice(2);

const projectName = commandLineArguments[0];

if (!projectName) {
    console.error("A project name must be specified");
    console.error("Please use the following command:");
    console.error("npm run rename-project -- new-project-name");
    console.error("Where 'new-project-name' is replaced with your desired plugin name, in kebab-case format.");
    process.exit(1);
}
