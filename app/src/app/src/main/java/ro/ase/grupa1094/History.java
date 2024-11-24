package ro.ase.grupa1094;

public class History {
    private String courseTitle;
    private String lessonTitle;
    private String lastAccessedDate;
    private int progress;

    public History(String courseTitle, String lessonTitle, String lastAccessedDate, int progress) {
        this.courseTitle = courseTitle;
        this.lessonTitle = lessonTitle;
        this.lastAccessedDate = lastAccessedDate;
        this.progress = progress;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public String getLastAccessedDate() {
        return lastAccessedDate;
    }

    public int getProgress() {
        return progress;
    }

    @Override
    public String toString() {
        return "HistoryItem{" +
                "courseTitle='" + courseTitle + '\'' +
                ", lessonTitle='" + lessonTitle + '\'' +
                ", lastAccessedDate='" + lastAccessedDate + '\'' +
                ", progress=" + progress +
                '}';
    }
}

