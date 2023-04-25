package com.nineplus.localhand.utils;

import com.google.firebase.database.*;
import com.nineplus.localhand.dto.UserDto;
import com.nineplus.localhand.exceptions.LocalHandException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class FirebaseService {

    public Boolean sendOtp(UserDto userDto) throws ExecutionException, InterruptedException {
        String child = ObjectUtils.isEmpty(userDto.getUsername()) ? userDto.getPhone() : userDto.getUsername();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference(CommonConstants.OTPs.OTP).child(child);
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean checkCompareOTP = Objects.equals(String.valueOf(dataSnapshot.getValue()), String.valueOf(userDto.getOtp()));
                if (checkCompareOTP) {
                    databaseReference.removeValueAsync();
                    future.complete(true);
                } else {
                    future.complete(false);
                }
                databaseReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("Error: " + error.getMessage());
                future.completeExceptionally(error.toException());
            }
        };
        databaseReference.addValueEventListener(valueEventListener);
        return future.get();
    }
}
