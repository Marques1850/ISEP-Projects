package ecourse.base.MeetingManagement.InvitationManagement.domain;

import eapli.framework.domain.model.ValueObject;


public class EmailContainer implements ValueObject {
    private String recipientEmail;
    private String senderEmail;

    protected EmailContainer() {}
    public EmailContainer(String recipientEmail, String senderEmail) {
        this.recipientEmail = recipientEmail;
        this.senderEmail = senderEmail;
    }

    public String recipientEmail() {
        return recipientEmail;
    }
    public String senderEmail() {
        return senderEmail;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof EmailContainer)) {
            return false;
        }
        EmailContainer other = (EmailContainer) obj;
        if (this.recipientEmail.equals(other.recipientEmail) && this.senderEmail.equals(other.senderEmail)) {
            return true;
        }
        return false;
    }
}
