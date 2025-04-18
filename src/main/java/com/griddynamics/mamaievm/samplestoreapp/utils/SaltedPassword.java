package com.griddynamics.mamaievm.samplestoreapp.utils;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SaltedPassword {
    String hashedPassword;
    String salt;
}
