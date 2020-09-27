package edu.hust.robothub.core.service;

import edu.hust.robothub.core.message.AbstractMessage;

public interface ServiceInvoker {
    AbstractMessage invoke(AbstractMessage abstractMessage);
}
