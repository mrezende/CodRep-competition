import sys
import getopt
import os

def get_path_to_tasks(chosen_datasets):
    path_to_tasks = ""
    if (chosen_datasets):
        for path_to_dataset in chosen_datasets:
            path_to_tasks = os.path.join(path_to_dataset, "Tasks/")

    return path_to_tasks


def get_path_to_solutions(chosen_datasets):
    path_to_solutions = ""
    if (chosen_datasets):
        for path_to_dataset in chosen_datasets:
            path_to_solutions = os.path.join(path_to_dataset, "Solutions/")

    return path_to_solutions

def get_path_to_java(chosen_datasets):
    path_to_java = ""
    if (chosen_datasets):
        for path_to_dataset in chosen_datasets:
            path_to_java = os.path.join(path_to_dataset, "Java/")

    return path_to_java

def create_path(path):
    if not os.path.exists(path):
        os.makedirs(path)


def create_java_files(chosen_datasets):
    path_to_tasks = get_path_to_tasks(chosen_datasets)
    path_to_solutions = get_path_to_solutions(chosen_datasets)
    path_to_java_folder = get_path_to_java(chosen_datasets)
    create_path(path_to_java_folder)

    for task in os.listdir(path_to_tasks):
        if(task.endswith(".txt")):
            path_to_task = os.path.abspath(os.path.join(path_to_tasks, task))
            path_to_solution = os.path.abspath(os.path.join(path_to_solutions, task))

            java_task = task.replace(".txt", ".java")
            path_to_java_file = os.path.abspath(os.path.join(path_to_java_folder, java_task))

            with open(path_to_task, 'r') as file:

                line_to_insert = file.readline()
                blank_line = file.readline()
                lines = file.readlines()

                with open(path_to_solution, 'r') as solution:
                    line_number = int(solution.readline())

                    lines[line_number - 1] = line_to_insert

                    with open(path_to_java_file, 'w') as java_file:
                        java_file.writelines(lines)



def main():
    global total_files, score

    # Parse the options
    verbose = False  # TODO, verbose mode?
    chosen_datasets = None
    try:
        opts, args = getopt.getopt(sys.argv[1:], "d:vh", ["datasets=", "help"])
    except getopt.GetoptError:
        raise
    for opt, arg in opts:
        if opt == "-v":
            verbose = True
        elif opt in ("-d", "--datasets"):
            chosen_datasets = arg.split(":")
        elif opt in ("-h", "--help"):
            print("usage evaluate.py [-vh] [-d path] [--datasets=path] [--help]")
            print("-v for verbose output mode")
            print(
                "-d or --datasets= to evaluate on chosen datasets, must be absolute path, multiple paths should be seperated with ':'. Default is evaluating on all datasets")
            sys.exit()

    # guess last line
    create_java_files(chosen_datasets)


if __name__ == "__main__":
    main()
