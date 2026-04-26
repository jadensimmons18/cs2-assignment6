#COP3503C Student Python Script v2.0
#DO NOT MODIFY THE CONTENTS OF THIS FILE!

import os
import sys
import subprocess
import filecmp


""" Enter file name without the extension, number of test cases, and assignment name. """
ASSIGNMENT_NAME = "UCF Student ID Skip List"
NUMBER_OF_TEST_CASES = 5
FILE_NAME = "SkipList"
JAVA_FILE = FILE_NAME + ".java"
JAVA_DRIVER_FILE = FILE_NAME + "Driver.java"
CLASS_NAME = FILE_NAME + ".class"
TIMEOUT_SECONDS = 35

def bit_bunny_message(student_name: str, name_wrap_threshold: int = 22, wrap_width: int = 28) -> str:
    quote = f'Hi there! I\'m Bit Bunny! You hopped through every bug, {student_name}!'
    should_wrap = len(student_name) > name_wrap_threshold

    lines = []

    if should_wrap:
        words = quote.split()
        if not words:
            wrapped_lines = [""]
        else:
            wrapped_lines = []
            current = words[0]
            for w in words[1:]:
                if len(current) + 1 + len(w) <= wrap_width:
                    current += " " + w
                else:
                    wrapped_lines.append(current)
                    current = w
            wrapped_lines.append(current)

        lines.append(f' (\\(\\        "{wrapped_lines[0]}"')

        for mid in wrapped_lines[1:]:
            lines.append(f' ( -.-)      "{mid}"')
    else:
        lines.append(f' (\\(\\        "{quote}"')

    lines.append(' ( -.-)      All tests passed! Your skip list is in great shape!')
    lines.append(' o_(")(")    Add more tests before grading burrows in!')

    return "\n".join(lines)

def debugger_duck_message():
    print(r"""
                  __
                <(o )___
                 (  ._>  Oops! Some tests failed... Check the student output files to see what did not pass.
                  `---'

                Don’t worry. Even ducks take a wrong turn sometimes!
                Keep debugging and try again.
                """)

def result():
    cwd = os.getcwd()

    passed = 0

    for tc in range(1, NUMBER_OF_TEST_CASES + 1):
        instructor_solution = "tc" + str(tc) + ".txt"
        student_solution = "tc" + str(tc) + "student.txt"

        f1 = open(cwd + '/' + instructor_solution, "r")
        f2 = open(cwd + '/' + student_solution, "r")

        result = filecmp.cmp(f1.name, f2.name, shallow=False)

        f1.close()
        f2.close()

        if result:
            passed = passed + 1
            subprocess.run(["rm tc" + str(tc) + "student.txt"], capture_output=True, text=True, shell=True)
        else:
            print("Test Case " + str(tc) + " did not pass!")

    if passed == NUMBER_OF_TEST_CASES:
        student_name = get_student_name(os.getcwd() + "/" + JAVA_FILE)
        print()
        print(bit_bunny_message(student_name))
        print()
    else:
        debugger_duck_message()

def get_student_name(java_file_path) -> str:
    with open(java_file_path, "r", encoding="utf-8") as file:
        for line in file:
            stripped = line.strip()

            if stripped.startswith("/*"):
                remainder = stripped[2:].strip()
                if remainder:
                    return remainder
                break

    return "NAME NOT RECORDED"

def compile_run_file():
    cwd = os.getcwd()

    javac = "javac " + cwd + "/" + JAVA_DRIVER_FILE
    proc = subprocess.run([javac], capture_output=True, text=True, shell=True)

    if len(str(proc.stderr)) > 0:
        print("Uh oh... Your code had compile warning/error messages! Try to fix them or points may be deducted.")
        print("------------------------------------------------------------------------")
        print(str(proc.stderr))
        print("------------------------------------------------------------------------")

    if not os.path.exists(cwd + "/" + CLASS_NAME):
        print(r"  ------  ")
        print(r" / o  o \ ")
        print(r"|        |")
        print(r"|  ____  |")
        print(r"| /    \ |")
        print(r" \      / ")
        print(r"  ------  ")
        print("Your code did not compile!")
        exit(1)

    for tc in range(1, NUMBER_OF_TEST_CASES + 1):
        f = open("tc" + str(tc) + "student.txt", "w")
        java = "java " + JAVA_DRIVER_FILE[:-5] + " " + str(tc)

        try:
            print("Running Test Case " + str(tc))
            proc2 = subprocess.run([java], capture_output=True, text=True, shell=True, timeout=TIMEOUT_SECONDS)

            if len(str(proc2.stderr)) > 0:
                print("Uh oh... Test Case " + str(tc) + " had error messages! Try to fix them or points may be deducted.")
                print("------------------------------------------------------------------------")
                print(str(proc2.stderr))
                f.write(str(proc2.stderr))
                print("------------------------------------------------------------------------")

            f.write(str(proc2.stdout))
            f.close()

        except subprocess.TimeoutExpired:
            print("Your program took too long to execute. Test Case " + str(tc) + " will receive a score of 0.")
            f.write("Test Case Timed Out.")
            f.close()

    print("All test cases have been tested.")

def setup_checker():
    print("Before testing your solution, we need to check whether all files are properly placed in the same directory.")
    cwd = os.getcwd()

    for tc in range(1, NUMBER_OF_TEST_CASES + 1):
        if not os.path.exists(cwd + "/tc" + str(tc) + ".txt"):
            raise FileNotFoundError("Missing tc" + str(tc) + ".txt in the directory. Script exiting!")

    if not os.path.exists(cwd + "/" + JAVA_FILE):
        raise FileNotFoundError("You are missing your Java source file! Script exiting!")

    if not os.path.exists(cwd + "/" + JAVA_DRIVER_FILE):
        raise FileNotFoundError("You are missing your Java driver source file! Script exiting!")

    print("All required files were found.")

def main():
    setup_checker()
    print("Testing the " + ASSIGNMENT_NAME + " assignment.")
    print("This assignment has " + str(NUMBER_OF_TEST_CASES) + " test cases.")
    compile_run_file()
    result()
    subprocess.run(["rm *.class"], capture_output=True, text=True, shell=True)

if __name__ == "__main__":
    main()