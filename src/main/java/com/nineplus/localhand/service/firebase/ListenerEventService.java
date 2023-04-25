package com.nineplus.localhand.service.firebase;

import com.google.firebase.database.*;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ListenerEventService {

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        readOTP("OTP");
    }

    public void readOTP(String referenceName) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference(referenceName);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot d :dataSnapshot.getChildren()) {
//                    System.out.println("Key: " + d.getKey());
//                    System.out.println("Value: " + d.getValue());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                System.out.println("Error: " + error.getMessage());
//            }
//        });
    }
}
