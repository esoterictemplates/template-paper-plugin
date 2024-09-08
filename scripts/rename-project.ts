const commandLineArguments = process.argv.slice(2);

const projectName = commandLineArguments[0];

if (!projectName) {
    console.log("A project name must be specified");
    process.exit(1);
}
