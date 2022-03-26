document.addEventListener("DOMContentLoaded", event => {
        const app = firebase.app();
        const db = firebase.firestore();
        const myMaps = db.collection('Map1')
        const query = myMaps.orderBy('edgeData','desc')
        query.get().then(maps =>
                maps.forEach(map => {
                        data = map.data()
                        document.write(`${data.edgeData} és <break>`);
                }
        ));
});

function googleLogin()
{
        const provider = new firebase.auth.GoogleAuthProvider();
        firebase.auth().signInWithPopup(provider).then(result =>{ 
                const user = result.user;
                document.write(`Hello ${user.displayName}`);
                console.log(user);
        }).catch(console.log);
}