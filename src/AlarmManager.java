import javax.sound.sampled.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AlarmManager {
    private static ArrayList<Alarm> alarms = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static ExecutorService soundExecutor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        soundExecutor.submit(() -> {
            while (true) {
                sortAlarmsByTime();
                checkAlarms();
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        while (true) {
            System.out.println("1. Додати будильник");
            System.out.println("2. Переглянути всі будильники");
            System.out.println("3. Сортувати будильники за часом");
            System.out.println("4. Знайти будильник за часом");
            System.out.println("5. Видалити будильник");
            System.out.println("6. Редагувати будильник");
            System.out.println("7. Вийти");
            System.out.print(" Оберіть опцію: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистимо буфер введення

            switch (choice) {
                case 1:
                    addAlarm();
                    break;
                case 2:
                    viewAlarms();
                    break;
                case 3:
                    sortAlarmsByTime();
                    break;
                case 4:
                    searchAlarmByTime();
                    break;
                case 5:
                    deleteAlarm();
                    break;
                case 6:
                    editAlarm();
                    break;
                case 7:
                    System.out.println("Дякую за використання будильника");
                    soundExecutor.shutdownNow();
                    System.exit(0);
                default:
                    System.out.println("Неправильний вибір. Будь ласка, спробуйте ще раз.");
            }
        }
    }

    private static void addAlarm() {
        System.out.print("Введіть час будильника (формат HH:mm): ");
        String time = scanner.nextLine();
        System.out.print("Введіть повідомлення для будильника: ");
        String message = scanner.nextLine();

        Alarm newAlarm = new Alarm(time, message);
        alarms.add(newAlarm);

        System.out.println("Будильник додано: " + newAlarm.getTime());
    }

    private static void viewAlarms() {
        System.out.println("Список будильників:");

        for (int i = 0; i < alarms.size(); i++) {
            System.out.println((i + 1) + ". Час: " + alarms.get(i).getTime() + ", Повідомлення: " + alarms.get(i).getMessage());
        }
    }

    private static void sortAlarmsByTime() {
        for (int i = 0; i < alarms.size() - 1; i++) {
            for (int j = 0; j < alarms.size() - 1 - i; j++) {
                String time1 = alarms.get(j).getTime();
                String time2 = alarms.get(j + 1).getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

                try {
                    Date date1 = sdf.parse(time1);
                    Date date2 = sdf.parse(time2);

                    if (date1.after(date2)) {
                        Collections.swap(alarms, j, j + 1);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void searchAlarmByTime() {
        System.out.print("Введіть час для пошуку (формат HH:mm): ");
        String targetTime = scanner.nextLine();
        boolean found = false;

        for (Alarm alarm : alarms) {
            if (alarm.getTime().equals(targetTime)) {
                System.out.println("Знайдено будильник: " + alarm.getTime() + ", Повідомлення: " + alarm.getMessage());
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Будильник за вказаним часом не знайдено.");
        }
    }

    private static void checkAlarms() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentTime = sdf.format(new Date());

        for (int i = 0; i < alarms.size(); i++) {
            if (alarms.get(i).getTime().equals(currentTime)) {
                System.out.println("Грає будильник: " + alarms.get(i).getTime() + ", Повідомлення: " + alarms.get(i).getMessage());
                playSound(alarms.get(i).getSoundFile());
                alarms.remove(i);
                i--;
            }
        }
    }

    private static void playSound(String soundFileName) {
        try {
            InputStream soundStream = AlarmManager.class.getClassLoader().getResourceAsStream(soundFileName);

            if (soundStream != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(new BufferedInputStream(soundStream));
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } else {
                System.out.println("Файл аудіо не знайдено: " + soundFileName);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private static void deleteAlarm() {
        System.out.print("Введіть номер будильника, який ви хочете видалити: ");
        int alarmIndex = scanner.nextInt();
        scanner.nextLine();

        if (alarmIndex >= 1 && alarmIndex <= alarms.size()) {
            Alarm deletedAlarm = alarms.remove(alarmIndex - 1);
            System.out.println("Будильник видалено: " + deletedAlarm.getTime());
        } else {
            System.out.println("Невірний номер будильника.");
        }
    }

    private static void editAlarm() {
        System.out.print("Введіть номер будильника, який ви хочете відредагувати: ");
        int alarmIndex = scanner.nextInt();
        scanner.nextLine();

        if (alarmIndex >= 1 && alarmIndex <= alarms.size()) {
            System.out.print("Введіть новий час будильника (формат HH:mm): ");
            String newTime = scanner.nextLine();
            System.out.print("Введіть нове повідомлення для будильника: ");
            String newMessage = scanner.nextLine();

            Alarm editedAlarm = alarms.get(alarmIndex - 1);
            editedAlarm = new Alarm(newTime, newMessage);
            alarms.set(alarmIndex - 1, editedAlarm);
            System.out.println("Будильник відредаговано: " + editedAlarm.getTime());
        } else {
            System.out.println("Невірний номер будильника.");
        }
    }

}