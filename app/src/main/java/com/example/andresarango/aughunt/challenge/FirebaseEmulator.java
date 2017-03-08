package com.example.andresarango.aughunt.challenge;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.location.DAMLocation;
import com.example.andresarango.aughunt.user.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Millochka on 3/1/17.
 */

public class FirebaseEmulator implements FirebaseHelper<Bitmap>{

    private static final String HINT_DATA = "hint_data";
    private Challenge<Bitmap> mChallenge;
    private Context mContext;
    private static final String IMAGE_DATA ="image_data" ;


    public FirebaseEmulator(Context context){

        this.mContext=context;

    }




    public void saveToShPref(final byte[] image, final String hint, DAMLocation location){
        String encodedImage = Base64.encodeToString(image, Base64.DEFAULT);

        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor edit=shre.edit();
        edit.putString(IMAGE_DATA,encodedImage);
        edit.putString(HINT_DATA,hint);
        edit.putLong("lat",Double.doubleToRawLongBits(location.getLat()));
        edit.putLong("lng",Double.doubleToRawLongBits(location.getLng()));
        edit.apply();


    }

    public Challenge<Bitmap> getmChallenge(){

        DAMLocation location = new DAMLocation(retrieveLLFrSh()[0],retrieveLLFrSh()[1]);

        Challenge<Bitmap> challenge= new Challenge<Bitmap>(retrieveBFrSh(),location,"userid");
        challenge.setmHint(retrieveHFrSh());




     return challenge;
    }

    public Bitmap retrieveBFrSh(){


        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(mContext);
        String previouslyEncodedImage = shre.getString(IMAGE_DATA, "");

        if( !previouslyEncodedImage.equalsIgnoreCase("") ){
            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            return bitmap;
        }
        return null;
    }

    public String retrieveHFrSh(){


        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(mContext);
        String hint = shre.getString(HINT_DATA, "");

        if( !hint.equalsIgnoreCase("") ){

            return hint;
        }
        return null;
    }

    public double[] retrieveLLFrSh(){

        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(mContext);
        double[] latLong = new double[2];

            latLong[0] =   Double.longBitsToDouble(shre.getLong("lat", 0));
        latLong[1]=   Double.longBitsToDouble(shre.getLong("lng", 0));


            return latLong;

    }


    @Override
    public List<Challenge<Bitmap>> getChallenges() {
        List<Challenge<Bitmap>>challenges = new ArrayList<>();
        Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.cat1);
        Challenge<Bitmap> challenge  =  new Challenge<Bitmap>(icon,null,"Danny");
        challenge.setmHint("Hint");
        challenge.setUsersAccepted(4);
        Bitmap icon2=BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.cat2);
        Challenge<Bitmap> challenge1 = new Challenge<Bitmap>(icon2,null,"Mila");
        challenge1.setmHint("Hint1");
        challenge1.setUsersAccepted(5);
        Bitmap icon3=BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.cat3);
        Challenge<Bitmap> challenge2 = new Challenge<Bitmap>(icon3,null,"Mila");
        challenge2.setmHint("Hint2");
        challenge2.setUsersAccepted(6);
        Bitmap icon4=BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.humans);
        Challenge<Bitmap> challenge3 = new Challenge<Bitmap>(icon4,null,"Mila");
        challenge3.setmHint("Hint3");
        challenge3.setUsersAccepted(7);
        challenges.add(challenge);
        challenges.add(challenge1);
        challenges.add(challenge2);
        challenges.add(challenge3);
        List<CompletedChallenge<Bitmap>> completedchallenges= new ArrayList<CompletedChallenge<Bitmap>>();
        CompletedChallenge<Bitmap> cc=new CompletedChallenge<>(icon, "Andres");
        CompletedChallenge<Bitmap> cc2=new CompletedChallenge<>(icon2, "Danny");
        CompletedChallenge<Bitmap> cc3=new CompletedChallenge<>(icon3, "Lily");
        completedchallenges.add(cc);
        completedchallenges.add(cc2);
        completedchallenges.add(cc3);
        completedchallenges.add(cc);
        completedchallenges.add(cc2);
        challenge1.setmCompletedChallenges(completedchallenges);
        challenge.setStatus(false);
        challenge1.setStatus(true);
        challenge2.setStatus(false);
        challenge3.setStatus(true);



        return  challenges;
    }

    @Override
    public void saveChallenge(Challenge<Bitmap> c) {

        this.mChallenge=c;
        Bitmap tempBitmap=(Bitmap) c.getmChallenge();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        tempBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        saveToShPref(byteArray, c.getmHint(),c.getmLocation());
    }

    @Override
    public void updateAcceptedChallenge(String challengeId) {

    }

    @Override
    public void completeChallenge(CompletedChallenge<Bitmap> c) {

    }

    @Override
    public void updateUserPoints(Integer points) {

    }

    @Override
    public User getCurrentUser() {



        return new User();
    }
}
