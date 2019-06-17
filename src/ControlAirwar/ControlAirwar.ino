



/*Using a Single Button, create mutliple options based on how long the button is pressed
 
 The circuit:
 * LED attached from pin 13 to ground through a 220 ohm resistor
 * LED attached from pin 12 to ground through a 220 ohm resistor
 * one side of momentary pushbutton attached to pin 2
 * other side of momentary pushbutton attached to Ground
 
 * Note 1: on most Arduinos there is already an LED on the board
 attached to pin 13.
 * Note 2: In this circuit, when the button is pressed, Ground Voltage is what will be applied. 
 
 Created DEC 2014 by Scuba Steve
 Modified JAN 2015 by Michael James
 Both members of https://programmingelectronics.com
 
 This code is in the public domain
 */


/////////Declare and Initialize Variables////////////////////////////

//We need to track how long the momentary pushbutton is held in order to execute different commands
//This value will be recorded in seconds
float pressLength_milliSeconds = 0;

// Define the *minimum* length of time, in milli-seconds, that the button must be pressed for a particular option to occur
int oneclick_milliSeconds = 150;
int longclick_milliSeconds = 800;        

//The Pin your button is attached to
int pinButton = 10;


//Pin your LEDs are attached to
int pinLED = 11; 

void setup(){

  // Initialize the pushbutton pin as an input pullup
  // Keep in mind, when pin 2 has ground voltage applied, we know the button is being pressed
  pinMode(pinButton, INPUT);     

  //set the LEDs pins as outputs
  pinMode(pinLED, OUTPUT); 

  //Start serial communication - for debugging purposes only
  Serial.begin(9600);                                     

} // close setup



void LightLED(){
  digitalWrite(pinLED,HIGH);
  delay(100);
  digitalWrite(pinLED,LOW);
  delay(10);
}

void checkCrashed(){
  if (Serial.available() > 0){
    char c = Serial.read();
    if (c == ';'){
      LightLED();
    }
  }

}

void loop() {
  checkCrashed();

  //Record *roughly* the tenths of seconds the button in being held down
  while (digitalRead(pinButton) == HIGH ){ 
    delay(25);  //if you want more resolution, lower this number 
    pressLength_milliSeconds = pressLength_milliSeconds + 25;   

    //display how long button is has been held


  }//close while

  if (pressLength_milliSeconds != 0){
    if(pressLength_milliSeconds > oneclick_milliSeconds){
      if (pressLength_milliSeconds < longclick_milliSeconds ){
        Serial.print("speed = ");
        Serial.println(1.25);
      }
      else{
        Serial.print("speed = ");
        Serial.println(pressLength_milliSeconds*0.016);
      }
    }
  }



  //every time through the loop, we need to reset the pressLength_Seconds counter
  pressLength_milliSeconds = 0;

} // close void loop










