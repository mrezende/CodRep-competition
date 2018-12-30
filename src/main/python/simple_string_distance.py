import sys
import getopt
import os.path
import pylev

# Total number of files in chosen datasets
total_files = 0
# Default score of all prediction
score = {}
# Number of correct predictions
correct_files = 0
# All prediction outputs by your algorithm
all_predictions = {}




def get_path_to_tasks(chosen_datasets):
    path_to_tasks = ""
    if (chosen_datasets):
        for path_to_dataset in chosen_datasets:
            path_to_tasks = os.path.join(path_to_dataset, "Tasks/")

    else:
        path_to_eval = os.path.abspath(os.path.dirname(__file__))
        path_to_datasets = os.path.join(path_to_eval, "../Datasets/")

        for dataset_dir in os.listdir(path_to_datasets):
            path_to_dataset = os.path.join(path_to_datasets, dataset_dir)
            if (os.path.isdir(path_to_dataset)):
                path_to_tasks = os.path.join(path_to_dataset, "Tasks/")

    return path_to_tasks

def guess_based_on_string_distance(chosen_datasets):
    path_to_tasks = get_path_to_tasks(chosen_datasets)
    for task in os.listdir(path_to_tasks):
        if(task.endswith(".txt")):
            path_to_task = os.path.abspath(os.path.join(path_to_tasks, task))
            with open(path_to_task, 'r') as file:

                line_to_insert = file.readline()
                blank_line = file.readline()
                lines = file.readlines()
                total_lines = len(lines)

                distances = {}

                for i in list(range(total_lines)):
                    line = lines[i]
                    distance = pylev.levenshtein(line_to_insert, line)

                    distances[i + 1] = distance

                line_min = min(distances.keys(), key=(lambda k: distances[k]))
                print(path_to_task + " " + str(line_min))

def guess_last_line(chosen_datasets):
    path_to_tasks = get_path_to_tasks(chosen_datasets)
    for task in os.listdir(path_to_tasks):
        if (task.endswith(".txt")):
            path_to_task = os.path.abspath(os.path.join(path_to_tasks, task))
            with open(path_to_task, 'r') as file:
                length = len(file.readlines()) - 2

                print(path_to_task + " " + str(length))


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
    guess_based_on_string_distance(chosen_datasets)


if __name__ == "__main__":
    main()
