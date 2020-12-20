public enum AppState {
    Running, Stopped;

    AppState flip() {
        return switch (this) {
            case Running -> Stopped;
            case Stopped -> Running;
        };
    }
}
