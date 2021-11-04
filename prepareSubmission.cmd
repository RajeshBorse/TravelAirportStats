del /f co7217_checked

IF EXIST submission_helper\solutions.dat (
	echo $null >> co7217_checked
	gradlew wrapper --gradle-version=7.1
	gradlew clean
	del /f submission.zip
	powershell "Compress-Archive . submission.zip"
) ELSE (
    echo Could not find /submission_helper/solutions.dat Please download the project template with this folder and execute the method SubmissionHelper.check_solution()
)