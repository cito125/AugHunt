package com.example.andresarango.aughunt.challenge.challenge_dialog_fragment;


import com.example.andresarango.aughunt.challenge.Challenge;

public interface DialogFragmentListener {
    <T> void startDialogueFragment(Challenge<T> challenge);
}
