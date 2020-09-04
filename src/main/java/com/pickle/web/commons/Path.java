package com.pickle.web.commons;


public enum Path {
    UPLOAD_PATH;
    @Override
    public String toString() {
        String result = "";
        switch(this) {
            case UPLOAD_PATH:
                result = "C:\\Users\\bit30\\IdeaProjects\\Team_Bitbox_Server\\src\\main\\resources\\static\\files\\";
                //result = "C:\\Users\\jihwe\\Collaboration Project\\Team_Bitbox_Server\\src\\main\\resources\\static\\files\\";
                break;

        }

        return result;
    }
}