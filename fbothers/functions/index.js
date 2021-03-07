const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });


const accountSid = functions.config().twilio.accountsid;
const authToken = functions.config().twilio.authtoken;
const client = require('twilio')(accountSid, authToken);

const send = async (message) => {return client.messages
  .create({
     body: `Your coach has given you feedback: ${message}`,
     from: '+14086377680',
     to: '+61422682029'
   })
}
  

(async () => {
  const res = await send()
  console.log(res)
})()

exports.sendNoteMessage = functions.firestore
.document('notes/{docId}')
.onCreate((snapshot, context) => {
    const data = snapshot.data()
    const {note} = data;

    console.log("Sending note: " + note)
    return send(note)
})
