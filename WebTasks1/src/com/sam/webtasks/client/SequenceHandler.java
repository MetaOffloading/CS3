//The SequenceHandler is the piece of code that defines the sequence of events
//that constitute the experiment.
//
//SequenceHandler.Next() will run the next step in the sequence.
//
//We can also switch between the main sequence of events and a subsequence
//using the SequenceHandler.SetLoop command. This takes two inputs:
//The first sets which loop we are in. 0 is the main loop. 1 is the first
//subloop. 2 is the second subloop, and so on.
//
//The second input is a Boolean. If this is set to true we initialise the 
//position so that the sequence will start from the beginning. If it is
//set to false, we will continue from whichever position we were currently in.
//
//So SequenceHandler.SetLoop(1,true) will switch to the first subloop,
//starting from the beginning.
//
//SequenceHandler.SetLoop(0,false) will switch to the main loop,
//continuing from where we left off.

//TODO:
//scroll
//data output
//resume where you left off

package com.sam.webtasks.client;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.sam.webtasks.basictools.CheckIdExists;
import com.sam.webtasks.basictools.CheckScreenSize;
import com.sam.webtasks.basictools.ClickPage;
import com.sam.webtasks.basictools.Consent;
import com.sam.webtasks.basictools.Counterbalance;
import com.sam.webtasks.basictools.InfoSheet;
import com.sam.webtasks.basictools.Initialise;
import com.sam.webtasks.basictools.Names;
import com.sam.webtasks.basictools.PHP;
import com.sam.webtasks.basictools.ProgressBar;
import com.sam.webtasks.basictools.Slider;
import com.sam.webtasks.basictools.TimeStamp;
import com.sam.webtasks.iotask1.IOtask1Block;
import com.sam.webtasks.iotask1.IOtask1BlockContext;
import com.sam.webtasks.iotask1.IOtask1InitialiseTrial;
import com.sam.webtasks.iotask1.IOtask1RunTrial;
import com.sam.webtasks.iotask2.IOtask2Block;
import com.sam.webtasks.iotask2.IOtask2BlockContext;
import com.sam.webtasks.iotask2.IOtask2RunTrial;
import com.sam.webtasks.perceptualTask.PerceptBlock;
import com.sam.webtasks.timeBasedOffloading.TimeBlock;
import com.sam.webtasks.iotask2.IOtask2InitialiseTrial;
import com.sam.webtasks.iotask2.IOtask2PreTrial;

public class SequenceHandler {
	static int cb, i=0;
	
	public static void Next() {	
		cb=Counterbalance.getFactorLevel("order");

		// move forward one step in whichever loop we are now in
		sequencePosition.set(whichLoop, sequencePosition.get(whichLoop) + 1);

		switch (whichLoop) {
		case 0: // MAIN LOOP
			switch (sequencePosition.get(0)) {
			/***********************************************************************
			 * The code here defines the main sequence of events in the experiment *
			 **********************************************************************/
			case 1:
				ClickPage.Run(Instructions.Get(0), "Next");
				break;
			case 2:
				ClickPage.Run(Instructions.Get(1), "Next");
				break;
			case 3:
				IOtask1Block block1 = new IOtask1Block();
				block1.nTargets = 0;
				block1.nTrials = 1;
				block1.offloadCondition = Names.REMINDERS_NOTALLOWED;
				block1.blockNum = -1;
				block1.Run();
				break;
			case 4:
				ClickPage.Run(Instructions.Get(2), "Next");
				break;
			case 5:
				IOtask1Block block2 = new IOtask1Block();
				block2.nTargets = 1;
				block2.nTrials = 1;
				block2.offloadCondition = Names.REMINDERS_NOTALLOWED;
				block2.blockNum = -2;
				block2.Run();
				break;
			case 6:
				ClickPage.Run(Instructions.Get(3), "Next");
				break;
			case 7:
				IOtask1Block block3 = new IOtask1Block();
				block3.nTargets = 3;
				block3.nTrials = 1;
				block3.offloadCondition = Names.REMINDERS_NOTALLOWED;
				block3.blockNum = -3;
				block3.Run();
				break;
			case 8:
				ClickPage.Run(Instructions.Get(31),  "Next");
				break;
			case 9:
				IOtask1Block block31 = new IOtask1Block();
				block31.nTargets = 3;
				block31.nTrials = 1;
				block31.offloadCondition = Names.REMINDERS_NOTALLOWED;
				block31.blockNum = -31;
				block31.askArithmetic = true;
				block31.Run();
				break;
			
			/*************/
			/*CONDITION 1*/
			/*************/			
			case 10:				
				switch(SessionInfo.sequence[cb][0]) {
				case Names.REMINDERS_NOTALLOWED:
					i=4;
					break;
				case Names.REMINDERS_MANDATORY_ANYCIRCLE:
					i=5;
					break;
				case Names.REMINDERS_PROSPECTIVE_MANDATORY:
					i=6;
					break;
				case Names.REMINDERS_RETROSPECTIVE_MANDATORY:
					i=7;
					break;			
				}
				
				ClickPage.Run(Instructions.Get(i), "Next");
				
				break;
			case 11:
				IOtask1Block block4 = new IOtask1Block();
				block4.nTargets = 3;
				block4.nTrials = 1;
				block4.offloadCondition = SessionInfo.sequence[cb][0];
				block4.blockNum = -4;
				block4.askArithmetic = true;
				block4.Run();
				break;
			case 12:
				Slider.Run(Instructions.Get(81), "0%", "100%");
				break;
			case 13:
				PHP.logData("slider" + SessionInfo.sequence[cb][0], ""+Slider.getSliderValue(), true);
				break;
			case 14:
				ClickPage.Run(Instructions.Get(9), "Next");
				break;
			case 15:
				ProgressBar.Initialise();
				ProgressBar.Show();
				ProgressBar.SetProgress(1, 8);
				
				IOtask1Block block5 = new IOtask1Block();
				block5.nTargets = 3;
				block5.nTrials = 5;
				block5.offloadCondition = SessionInfo.sequence[cb][0];
				block5.blockNum = 1;
				block5.askArithmetic = true;
				block5.Run();
				break;
				
				/*************/
				/*CONDITION 2*/
				/*************/
				
			case 16:
				ProgressBar.SetProgress(2, 8);
				
				switch(SessionInfo.sequence[cb][1]) {
				case Names.REMINDERS_NOTALLOWED:
					i=8;
					break;
				case Names.REMINDERS_MANDATORY_ANYCIRCLE:
					i=5;
					break;
				case Names.REMINDERS_PROSPECTIVE_MANDATORY:
					i=6;
					break;
				case Names.REMINDERS_RETROSPECTIVE_MANDATORY:
					i=7;
					break;			
				}
				
				ClickPage.Run(Instructions.Get(i), "Next");
				
				break;		
			case 17:
				IOtask1Block block6 = new IOtask1Block();
				block6.nTargets = 3;
				block6.nTrials = 1;
				block6.offloadCondition = SessionInfo.sequence[cb][1];
				block6.blockNum = -5;
				block6.askArithmetic = true;
				block6.Run();
				break;
			case 18:
				Slider.Run(Instructions.Get(81), "0%", "100%");
				break;
			case 19:
				PHP.logData("slider" + SessionInfo.sequence[cb][1], ""+Slider.getSliderValue(), true);
				break;
			case 20:
				ClickPage.Run(Instructions.Get(9), "Next");
				break;
			case 21:
				ProgressBar.SetProgress(3, 8);
				
				IOtask1Block block7 = new IOtask1Block();
				block7.nTargets = 3;
				block7.nTrials = 5;
				block7.offloadCondition = SessionInfo.sequence[cb][1];
				block7.blockNum = 2;
				block7.askArithmetic = true;
				block7.Run();
				break;
				
				/*************/
				/*CONDITION 3*/
				/*************/
				
			case 22:
				switch(SessionInfo.sequence[cb][2]) {
				case Names.REMINDERS_NOTALLOWED:
					i=8;
					break;
				case Names.REMINDERS_MANDATORY_ANYCIRCLE:
					i=5;
					break;
				case Names.REMINDERS_PROSPECTIVE_MANDATORY:
					i=6;
					break;
				case Names.REMINDERS_RETROSPECTIVE_MANDATORY:
					i=7;
					break;			
				}
				
				ClickPage.Run(Instructions.Get(i), "Next");
				
				break;		
			case 23:
				ProgressBar.SetProgress(4, 8);
				
				IOtask1Block block8 = new IOtask1Block();
				block8.nTargets = 3;
				block8.nTrials = 1;
				block8.offloadCondition = SessionInfo.sequence[cb][2];
				block8.blockNum = -6;
				block8.askArithmetic = true;
				block8.Run();
				break;
			case 24:
				Slider.Run(Instructions.Get(81), "0%", "100%");
				break;
			case 25:
				PHP.logData("slider" + SessionInfo.sequence[cb][2], ""+Slider.getSliderValue(), true);
				break;
			case 26:
				ClickPage.Run(Instructions.Get(9), "Next");
				break;
			case 27:
				ProgressBar.SetProgress(5, 8);
				
				IOtask1Block block9 = new IOtask1Block();
				block9.nTargets = 3;
				block9.nTrials = 5;
				block9.offloadCondition = SessionInfo.sequence[cb][2];
				block9.blockNum = 3;
				block9.askArithmetic = true;
				block9.Run();
				break;
			
				/*************/
				/*CONDITION 4*/
				/*************/
				
			case 28:
				switch(SessionInfo.sequence[cb][3]) {
				case Names.REMINDERS_NOTALLOWED:
					i=8;
					break;
				case Names.REMINDERS_MANDATORY_ANYCIRCLE:
					i=5;
					break;
				case Names.REMINDERS_PROSPECTIVE_MANDATORY:
					i=6;
					break;
				case Names.REMINDERS_RETROSPECTIVE_MANDATORY:
					i=7;
					break;			
				}
				
				ClickPage.Run(Instructions.Get(i), "Next");
				
				break;		
			case 29:
				ProgressBar.SetProgress(6, 8);
				
				IOtask1Block block10 = new IOtask1Block();
				block10.nTargets = 3;
				block10.nTrials = 1;
				block10.offloadCondition = SessionInfo.sequence[cb][3];
				block10.blockNum = -7;
				block10.askArithmetic = true;
				block10.Run();
				break;
			case 30:
				Slider.Run(Instructions.Get(81), "0%", "100%");
				break;
			case 31:
				PHP.logData("slider" + SessionInfo.sequence[cb][3], ""+Slider.getSliderValue(), true);
				break;
			case 32:
				ClickPage.Run(Instructions.Get(9), "Next");
				break;
			case 33:
				ProgressBar.SetProgress(7, 8);
				
				IOtask1Block block11 = new IOtask1Block();
				block11.nTargets = 3;
				block11.nTrials = 5;
				block11.offloadCondition = SessionInfo.sequence[cb][3];
				block11.blockNum = 4;
				block11.askArithmetic = true;
				block11.Run();
				break;
			case 34:
				ProgressBar.SetProgress(8, 8);
				
				// log data and check that it saves
				String data = TimeStamp.Now() + ",";
				data = data + SessionInfo.participantID + ",";
				data = data + SessionInfo.gender + ",";
				data = data + SessionInfo.age + ",";
				data = data + Counterbalance.getFactorLevel("order");

				PHP.UpdateStatus("finished");
				PHP.logData("finish", data, true);
				break;
			case 35:
				ProgressBar.Hide();
				
				ClickPage.Run(Instructions.Get(10), "nobutton");
				break;	
			}
			break;

		/********************************************/
		/* no need to edit the code below this line */
		/********************************************/

		case 1: // initialisation loop
			switch (sequencePosition.get(1)) {
			case 1:
				// initialise experiment settings
				Initialise.Run();
				break;
			case 2:
				// make sure that a participant ID has been registered.
				// If not, the participant may not have accepted the HIT
				CheckIdExists.Run();
				break;
			case 3:
				// check the status of this participant ID.
				// have they already accessed or completed the experiment? if so,
				// we may want to block them, depending on the setting of
				// SessionInfo.eligibility
				PHP.CheckStatus();
				break;
			case 4:
				// check whether this participant ID has been used to access a previous experiment
				PHP.CheckStatusPrevExp();
				break;
			case 5:
				// clear screen, now that initial checks have been done
				RootPanel.get().clear();

				// make sure the browser window is big enough
				CheckScreenSize.Run(SessionInfo.minScreenSize, SessionInfo.minScreenSize);
				break;
			case 6:
				if (SessionInfo.runInfoConsentPages) { 
					InfoSheet.Run(Instructions.InfoText());
				} else {
					SequenceHandler.Next();
				}
				break;
			case 7:
				if (SessionInfo.runInfoConsentPages) { 
					Consent.Run();
				} else {
					SequenceHandler.Next();
				}
				break;
			case 8:
				//record the participant's counterbalancing condition in the status table				
				if (!SessionInfo.resume) {
					PHP.UpdateStatus("" + Counterbalance.getCounterbalancingCell() + ",1,0,0,0,0");
				} else {
					SequenceHandler.Next();
				}
				break;
			case 9:
				SequenceHandler.SetLoop(0, true); // switch to and initialise the main loop
				SequenceHandler.Next(); // start the loop
				break;
			}
			break;
		case 2: // IOtask1 loop
			switch (sequencePosition.get(2)) {
			/*************************************************************
			 * The code here defines the sequence of events in subloop 2 *
			 * This runs a single trial of IOtask1                       *
			 *************************************************************/
			case 1:
				// first check if the block has ended. If so return control to the main sequence
				// handler
				IOtask1Block block = IOtask1BlockContext.getContext();

				if (block.currentTrial == block.nTrials) {
					SequenceHandler.SetLoop(0, false);
				}

				SequenceHandler.Next();
				break;
			case 2:
				// now initialise trial and present instructions
				IOtask1InitialiseTrial.Run();
				break;
			case 3:
				// now run the trial
				IOtask1RunTrial.Run();
				break;
			case 4:
				// we have reached the end, so we need to restart the loop
				SequenceHandler.SetLoop(2, true);
				SequenceHandler.Next();
				break;
			}
			break;
		case 3: //IOtask2 loop
			switch (sequencePosition.get(3)) {
			/*************************************************************
			 * The code here defines the sequence of events in subloop 3 *
			 * This runs a single trial of IOtask2                       *
			 *************************************************************/
			case 1:
				// first check if the block has ended. If so return control to the main sequence
				// handler
				IOtask2Block block = IOtask2BlockContext.getContext();
				
				if (block.currentTrial == block.nTrials) {
					SequenceHandler.SetLoop(0,  false);
				}
				
				SequenceHandler.Next();
				break;
			case 2:
				IOtask2InitialiseTrial.Run();
				break;
			case 3:;
				//present the pre-trial choice if appropriate
				if (IOtask2BlockContext.currentTargetValue() > -1) {
					IOtask2PreTrial.Run();
				} else { //otherwise just skip to the start of the block
					if ((IOtask2BlockContext.getTrialNum() > 0)&&(IOtask2BlockContext.countdownTimer())) {
						//if we're past the first trial and there's a timer, click to begin
						ClickPage.Run("Ready?", "Continue");
					} else {
						SequenceHandler.Next();
					}
				}
				break;
			case 4:
				if (IOtask2BlockContext.getNTrials() == -1) { //if nTrials has been set to -1, we quit before running
					SequenceHandler.SetLoop(0,  false);
					SequenceHandler.Next();
				} else {
					//otherwise, run the trial
					IOtask2RunTrial.Run();
				}
				break;
			case 5:
				IOtask2PostTrial.Run();
				break;
			case 6:
				//we have reached the end, so we need to restart the loop
				SequenceHandler.SetLoop(3,  true);
				SequenceHandler.Next();
				break;
			}
		}
	}
	
	private static ArrayList<Integer> sequencePosition = new ArrayList<Integer>();
	private static int whichLoop;

	public static void SetLoop(int loop, Boolean init) {
		whichLoop = loop;

		while (whichLoop + 1 > sequencePosition.size()) { // is this a new loop?
			// if so, initialise the position in this loop to zero
			sequencePosition.add(0);
		}

		if (init) { // go the beginning of the sequence if init is true
			sequencePosition.set(whichLoop, 0);
		}
	}
	
	// get current loop
	public static int GetLoop() {
		return (whichLoop);
	}

	// set a new position
	public static void SetPosition(int newPosition) {
		sequencePosition.set(whichLoop, newPosition);
	}

	// get current position
	public static int GetPosition() {
		return (sequencePosition.get(whichLoop));
	}
	
	// get current position from particular loop
	public static int GetPosition(int nLoop) {
		return (sequencePosition.get(nLoop));
	}
}
