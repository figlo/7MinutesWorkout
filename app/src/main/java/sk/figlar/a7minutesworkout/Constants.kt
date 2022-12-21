package sk.figlar.a7minutesworkout

object Constants {
    fun defaultExerciseList(): List<ExerciseModel> {
        val exerciseList: MutableList<ExerciseModel> = emptyList<ExerciseModel>().toMutableList()
        val abdominalCrunch = ExerciseModel(
            1,
            "Abdominal Crunch",
            R.drawable.ic_abdominal_crunch,
            false,
            false,
        )
        exerciseList += abdominalCrunch

        val highKnees = ExerciseModel(
            2,
            "High Knees Running In Place",
            R.drawable.ic_high_knees_running_in_place,
            false,
            false,
        )
        exerciseList += highKnees

        val jumpingJacks = ExerciseModel(
            3,
            "Jumping Jacks",
            R.drawable.ic_jumping_jacks,
            false,
            false,
        )
        exerciseList += jumpingJacks

        val lunge = ExerciseModel(
            4,
            "Lunge",
            R.drawable.ic_lunge,
            false,
            false,
        )
        exerciseList += lunge

        val plank = ExerciseModel(
            5,
            "Plank",
            R.drawable.ic_plank,
            false,
            false,
        )
        exerciseList += plank

        val pushUp = ExerciseModel(
            6,
            "Push Up",
            R.drawable.ic_push_up,
            false,
            false,
        )
        exerciseList += pushUp

        val pushUpAndRotation = ExerciseModel(
            7,
            "Push Up And Rotation",
            R.drawable.ic_push_up_and_rotation,
            false,
            false,
        )
        exerciseList += pushUpAndRotation

        val sidePlank = ExerciseModel(
            8,
            "Side Plank",
            R.drawable.ic_side_plank,
            false,
            false,
        )
        exerciseList += sidePlank

        val squat = ExerciseModel(
            9,
            "Squat",
            R.drawable.ic_squat,
            false,
            false,
        )
        exerciseList += squat

        val stepUp = ExerciseModel(
            10,
            "Step Up Onto Chair",
            R.drawable.ic_step_up_onto_chair,
            false,
            false,
        )
        exerciseList += stepUp

        val triceps = ExerciseModel(
            11,
            "Triceps Dip On Chair",
            R.drawable.ic_triceps_dip_on_chair,
            false,
            false,
        )
        exerciseList += triceps

        val wallSit = ExerciseModel(
            12,
            "Wall Sit",
            R.drawable.ic_wall_sit,
            false,
            false,
        )
        exerciseList += wallSit

        return exerciseList.toList()
    }
}