package io.model;


public enum State{


    Lost,
    Playing,
    Error, Join;
    
    private String message;

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

	public String getMessage() {
		return message;
		
	}

}