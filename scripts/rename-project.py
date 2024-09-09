import sys

# The command is:
# py scripts/rename-project.py project-top-level-domain project-author-name project-name
command_line_arguments = sys.argv[1::]

try:
    newProjectTopLevelDomain = command_line_arguments[0]
except:
    print("No new top-level-domain specified! Nothing to rename the project to.")
    print()
    print("Please use the following command format:")
    print("py scripts/rename-project.py project-top-level-domain project-author-name project-name")
    print()
    print("Where:")
    print("'project-top-level-domain' is the top level domain, such as net, com, dev, org, etc.")
    print("'project-author-name' is the new author name of the project.")
    print("'project-name' is the new project name.")
    print()
    print("Surround arguments with quotes if they contain spaces!")
    exit(1)

newProjectAuthorName = command_line_arguments[1]
newProjectName = command_line_arguments[2]
