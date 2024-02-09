import java.util.ArrayList;
import java.util.List; // Import List interface
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class QuizQuestion {
    private String question;
    private ArrayList<String> options;
    private int correctOptionIndex;

    public QuizQuestion(String question, ArrayList<String> options, int correctOptionIndex) {
        this.question = question;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }
}

public class Quiz {
    private ArrayList<QuizQuestion> questions;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;

    public Quiz(ArrayList<QuizQuestion> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.score = 0;
    }

    public void startQuiz() {
        System.out.println("You have 20 seconds to answer each question");
        askQuestion();
    }

    private void askQuestion() {
        if (currentQuestionIndex < questions.size()) {
            QuizQuestion currentQuestion = questions.get(currentQuestionIndex);
            System.out.println("Question: " + currentQuestion.getQuestion());
            ArrayList<String> options = currentQuestion.getOptions();
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }


            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("\nTime's up!");
                    processAnswer(-1);
                }
            }, 20000);


            Timer messageTimer = new Timer();
            messageTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("5 seconds left, Hurry up!");
                }
            }, 15000);

            // Read user input
            Scanner scanner = new Scanner(System.in);
            int selectedOption = scanner.nextInt();
            processAnswer(selectedOption - 1);
        } else {
            showResult();
        }
    }

    private void processAnswer(int selectedOption) {
        timer.cancel();

        QuizQuestion currentQuestion = questions.get(currentQuestionIndex);
        if (selectedOption == currentQuestion.getCorrectOptionIndex()) {
            System.out.println("Correct!");
            score++;
        } else {
            System.out.println("Incorrect!");
        }

        currentQuestionIndex++;
        askQuestion();
    }

    private void showResult() {
        System.out.println("\nQuiz Finished!");
        System.out.println("Your Score: " + score + "/" + questions.size());
    }

    public static void main(String[] args) {

        ArrayList<QuizQuestion> questions = new ArrayList<>();
        questions.add(new QuizQuestion("What is the capital of India?",
                new ArrayList<>(List.of("Delhi", "Mumbai", "Kolkata", "Chennai")), 0));
        questions.add(new QuizQuestion("Which planet is known as the Red Planet?",
                new ArrayList<>(List.of("venus", "Mars", "Jupiter", "Mercury")), 1));
        questions.add(new QuizQuestion("Which river is often referred to as the \"Ganga of the South\" in India?",
                new ArrayList<>(List.of("Yamuna", "Godavari", "Krushna", "Kaveri")), 3));
        questions.add(new QuizQuestion("What is the national currency of India?",
                new ArrayList<>(List.of("Taka", "Rupiah", "Rupee", "Dirham")), 2));
        questions.add(new QuizQuestion("Who was the first Prime Minister of India?",
                new ArrayList<>(List.of("Lal Bahadur Shastri", "Dr Abdul Kalam", "Indira Gandhi", "Javahrlal Nehru")), 3
        ));


        Quiz quiz = new Quiz(questions);
        quiz.startQuiz();
    }
}
