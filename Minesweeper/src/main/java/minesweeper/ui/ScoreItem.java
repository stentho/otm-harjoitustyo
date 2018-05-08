package minesweeper.ui;


import javafx.beans.property.SimpleStringProperty;

    public class ScoreItem {
 
        private SimpleStringProperty name;
        private SimpleStringProperty width;
        private SimpleStringProperty height;
        private SimpleStringProperty mines;
        private SimpleStringProperty time;

        public ScoreItem(String name, String width, String height, String mines, String time) {
            this.name = new SimpleStringProperty(name);
            this.width = new SimpleStringProperty(width);
            this.height = new SimpleStringProperty(height);
            this.mines = new SimpleStringProperty(mines);
            this.time = new SimpleStringProperty(time);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public String getWidth() {
            return width.get();
        }

        public void setWidth(String width) {
            this.width.set(width);
        }

        public String getHeight() {
            return height.get();
        }

        public void setHeight(String height) {
            this.height.set(height);
        }

        public String getMines() {
            return mines.get();
        }

        public void setMines(String mines) {
            this.mines.set(mines);
        }

        public String getTime() {
            return time.get();
        }

        public void setTime(String time) {
            this.time.set(time);
        }
    }