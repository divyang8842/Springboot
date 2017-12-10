import React, {Component} from 'react';
import {Link,withRouter} from 'react-router-dom';
import PropTypes from 'prop-types';
import * as API from '../api/API';
import logo from '../images/dropbox_logo740.png'
import NavBar from "../components/Navbar"
import Avatar from 'material-ui/Avatar';
import FileFolder from 'material-ui/svg-icons/file/folder';
import FileDownload from 'react-file-download';
import RaisedButton from 'material-ui/RaisedButton';

import {
    blue300,
    indigo900,
    orange200,
    deepOrange300,
    pink400,
    purple500
} from 'material-ui/styles/colors';



import { Button, ListGroup,ListGroupItem,ProgressBar, Form, FormControl, FormGroup, Col, ControlLabel, Checkbox } from 'react-bootstrap';

const style = {margin: 5};


var fullscreen={position: 'fixed',
top: '0',
left: '0',
bottom: '0',
right:'0',
overflow: 'auto',
background: 'white'}

var uploadButton={
    lineHeight: '1',
padding: '0 16',
fontWeight :'500',
border: 'none',
color: '#FFF',
backgroundColor: '#0070E0',

    float:'right',
}

var uploadButton1={

    float:'right',
}


var choose={position:'relative',top:-5,right:220}
var uploadDiv={ position:'relative',top:20,right:100}

var fileListItems={color:'#0070E0'}

var list={marginTop:20, paddingLeft:40, paddingRight:270}

var del={float:'right',marginRight:'10px'}
var fll ={float:'left'};
var flr =  {float:'right'};
var star = {float:'right',marginRight:'50px'}
var mr30 = {marginRight:'30px'}

var backButton={position:'relative',marginLeft:40}
class Welcome extends Component {

    static propTypes = {
        username: PropTypes.string.isRequired,
        uploadFile: PropTypes.func.isRequired

    };
    state = {

        filelist:[],
        staredList:[],
        sharedList:[],
        pathTrack:[],
        root:'',
        userId:'',
        rootDir:'',
        message:'',
        fileToShare:'',
        sharedShow:false,
        staredShow:false,
        myfileShow:true

    };


    componentWillMount(){

        var userid = localStorage.getItem("token");
        var root = localStorage.getItem("root");

            this.setState({
                root:root,
                userId:userid

            });
        this.getChildDir(root);

            document.title = `DropBox !!`;

    }

    goToPath = (path) =>{
        this.props.goToPath(path);
    }

    getHome =() =>{
        this.props.goToPath('/welcome');
       // this.props.history.push('/welcome');
    }
    getBack = () =>{

        if(this.state.pathTrack.length>1){
            this.getChildDir(this.state.pathTrack[this.state.pathTrack.length-2]);
            this.state.pathTrack.splice(this.state.pathTrack.length-2, 2);
        }else{
            this.getChildDir(this.state.root);
            this.state.pathTrack.splice(this.state.pathTrack.length-1, 1);
        }

    };


    handleFile= (event) =>{
        const payload = new FormData();
        var pathToUpload = "";
        if(this.state.pathTrack.length>0){
            pathToUpload = (this.state.pathTrack[this.state.pathTrack.length-1]);
        }else{
            pathToUpload = (this.state.root);
        }
        payload.append('myFile', this.refs.myFile.files[0]);
        payload.append('path',pathToUpload);
        API.doUpload(payload)
            .then((res) => {


               if(res.status=='501'){
                    this.signout();
                }else{
                   this.getChildDir(pathToUpload);
               }
            });
    };



    createDir= (dirname) =>{
        var pathToUpload = "";
        if(this.state.pathTrack.length>0){
            pathToUpload = (this.state.pathTrack[this.state.pathTrack.length-1]);
        }else{
            pathToUpload = (this.state.root);
        }
        var data = {'filename':dirname, 'filepath': pathToUpload};
        API.doMkdir(data)
            .then((status) => {
                if (status === 200) {
                    this.getChildDir(pathToUpload);
                }else if(status==501){
                    this.signout();
                }
            });
    };

    shareFile = (emails) =>{
     /*   API.validateEmails({'emails':emails})
            .then((res) =>{
            var proceed = false;
                if(res.status=='200'){
                    if(res.linkShare=='true'){
                        if(window.confirm("Some of the email provided are not registered with DropBox.\n The file will be shared as a link?")){
                            proceed=true;
                        }
                    }else{
                        proceed = true;
                    }
                   if(proceed){*/
                       var data  = {'fileToShare':this.state.fileToShare,'emails':emails};
                       API.doShareFile(data).then((res)=>{

                       });
                 /*  }
                   this.setState({
                       fileToShare:''
                   });
                }else if(res.status=='501'){
                    this.signout();
                }

    });   */

    }
    deleteDir= (filename) =>{
        var pathToUpload = "";
        if(this.state.pathTrack.length>0){
         pathToUpload = (this.state.pathTrack[this.state.pathTrack.length-1]);
        }else{
            pathToUpload = (this.state.root);
        }
        var data = {'filename':filename, 'filepath': pathToUpload};
        API.deleteDir(data)
            .then((status) => {

                if (status === 200) {
                    this.getChildDir(pathToUpload);
                }else if(status==501){
                    this.signout();
                }
            });
    };




    getChildDir= (filepath) =>{
        var data = {'filepath':filepath};
        API.getChildDirs(data)
            .then((res) => {


                if (res.status == 200) {
                    if(filepath!=this.state.pathTrack[this.state.pathTrack.length-1]){
                        this.state.pathTrack.push(filepath);
                    }

                    var newFileList=[];
                    var newSharedList = [];
                    var newStaredList = [];
                    if(res.filelist && res.filelist!=null && res.filelist!='null') {

                            newFileList = res.filelist;

                    }

                    if(res.staredlist && res.staredlist!=null && res.staredlist!='null') {
                            newStaredList= res.staredlist;
                    }
                    if(res.sharedlist && res.sharedlist!=null && res.sharedlist!='null') {
                        newSharedList= res.sharedlist;
                    }
                    this.setState({
                        filelist: newFileList,
                        staredList : newStaredList,
                        sharedList: newSharedList,
                        isSelfCall: true
                    });
                    this.showMyFiles();
                }else if(res.status==='501'){
                    localStorage.removeItem("token");
                    localStorage.removeItem("root");
                    this.signout();
                }
            })
    };

   download= (filepath,filename) =>{

        var data = {'path':filepath,'name':filename};
        API.doDownload(data)
            .then((res) => {
            console.log("res.data : ",res.data);
                    FileDownload(res.data,filename);
            })
    };

    getUserProfile =()=>{
        this.props.goToPath('/userprofile');
        // this.props.history.push('/userprofile');
    }

    getUserLogs =() =>{
        this.props.goToPath('/useractivity');
    }

    signout = () => {
        this.props.signout();
    };

    handleChange(evt,filePath) {
        var pathToUpload = "";
        if(this.state.pathTrack.length>0){
            pathToUpload = (this.state.pathTrack[this.state.pathTrack.length-1]);
        }else{
            pathToUpload = (this.state.root);
        }

        var checked = evt.target.checked;
        var data = {'filepath':filePath};
        if(checked){
            API.doStar(data) .then((res) => {
                this.getChildDir(pathToUpload);

            });
        }else{
            API.doUnStar(data) .then((res) => {
                this.getChildDir(pathToUpload);
            });
        }};

    shareFileData = (path,filename) =>{
        this.setState({
            fileToShare:path
        });
    }

    showStaredFiles = () => {
        this.setState({
            sharedShow:false,
            staredShow:true,
            myfileShow:false
        });

    }

    showSharedFiles = () => {
        this.setState({
            sharedShow:true,
            staredShow:false,
            myfileShow:false
        });
    }

    showMyFiles = () => {
        this.setState({
            sharedShow:false,
            staredShow:false,
            myfileShow:true
        });
    }



    render(){
        var filelist1 = [];
        var staredList =[];
        var sharedList = [];
        if(this.state.filelist && this.state.filelist!=null) {
            filelist1 = this.state.filelist;
        }
        if(this.state.staredList && this.state.staredList!=null && this.state.staredList!='null'){
            staredList = this.state.staredList;
        }
        if(this.state.sharedList && this.state.sharedList!=null && this.state.sharedList!='null'){
            sharedList = this.state.sharedList;
        }



        //alert(JSON.stringify(staredList));
        var username = this.state.userid;

        return(<div style={fullscreen}>
                <NavBar goToPath={this.goToPath} fileToShare={this.state.fileToShare} shareFile={this.shareFile} createDir = {this.createDir} getHome={this.getHome} getUserLogs={this.getUserLogs} getUserProfile={this.getUserProfile}  signout= {this.signout}  ></NavBar>
                <hr id="divider"></hr>
                <div >




                    <div class="modal-body row">

                        <div className="col-md-2" style={{border: "2px solid grey",height:"900px"}}>
                            <button type="button" style={{width:"200px"}} className="btn btn-default"  onClick={this.showStaredFiles}>Stared Files</button><br/>
                            <button type="button" style={{width:"200px"}} className="btn btn-default"  onClick={this.showSharedFiles}>Shared Files</button><br/>
                            <button type="button" style={{width:"200px"}} className="btn btn-default"  onClick={this.showMyFiles}>My Files</button>
                        </div>
                        <div className="col-md-10">
                            <div style={choose}>

                                <Button bsStyle="primary" style={uploadButton}  active onClick={() => this.handleFile()}>
                                    <span className="glyphicon glyphicon-upload "  aria-hidden="true"></span>
                                </Button>

                              <input type="file" style={uploadButton1}  ref="myFile"  name="myFile" />


                               {/* <input
                                    style={uploadButton1}
                                    type="file"
                                    ref="myFile"
                                    name="myFile"
                                    bsStyle="primary" bsSize="large"
                                />*/}

                            </div>


                            <div> <Button style={backButton} onClick={() => this.getBack()}>
                                Back
                            </Button></div>
                       <div style={{ display: (this.state.staredShow ? 'block' : 'none') }}>

                    <div style={list} >
                        Stared files
                    </div>
                    <ListGroup style={list}> {staredList.map((file, i) =>
                        <ListGroupItem style={fileListItems}  key={i}>{ file.file==0 ?
                            (   <div>
                                    <Avatar  className="file-opt" icon={<FileFolder />}
                                             color={orange200}
                                             backgroundColor={blue300}
                                             size={30}
                                             style={style}/>
                                    <a onClick={() => this.getChildDir(file.filepath)}> {file.filename} </a>
                                    <Button style={del}  onClick={()=>this.deleteDir(file.filename)} >
                                        <span className="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                    </Button>

                                    <Button style={del}  onClick={()=>this.getChildDir(file.filepath)}   ><span className=" glyphicon glyphicon-chevron-right" aria-hidden="true"></span> </Button>
                                    <Button style={del}  onClick={()=>this.shareFileData(file.filepath,file.filename)}> <span className="glyphicon glyphicon-share" aria-hidden="true"></span> </Button>
                                    <Checkbox  checked={true} style={star} value={file.filepath} onChange={(e) => this.handleChange(e,file.filepath)} >Star</Checkbox>

                                </div>

                            ):
                            (<div><Avatar  className="file-opt" icon={<FileFolder />}
                                           color={orange200}
                                           backgroundColor={purple500}
                                           size={30}
                                           style={style}/>
                                    <a  onClick={()=>this.download(file.filepath,file.filename)} >{file.filename}</a>

                                <Button style={del} onClick={()=>this.deleteDir(file.filename)}> <span className="glyphicon glyphicon-remove" aria-hidden="true"></span></Button>
                                <Button style={del}  onClick={()=>this.download(file.filepath,file.filename)}  > <span className="glyphicon glyphicon-download" aria-hidden="true"></span></Button>
                                <Button style={del}  onClick={()=>this.shareFileData(file.filepath,file.filename)}> <span className="glyphicon glyphicon-share" aria-hidden="true"></span> </Button>
                                <Checkbox  checked={true} style={star}  value={file.filepath} onChange={(e) => this.handleChange(e,file.path)} >Star</Checkbox>
                            </div> )}</ListGroupItem>
                    )}
                    </ListGroup>
                       </div>
                            <div style={{ display: (this.state.sharedShow ? 'block' : 'none') }}>
                    <div style={list}>
                        Shared files
                    </div>
                    <ListGroup style={list}> {sharedList.map((file, i) =>
                        <ListGroupItem style={fileListItems}  key={i}>{ file.file==0  ?
                            (   <div>
                                    <Avatar  className="file-opt" icon={<FileFolder />}
                                             color={orange200}
                                             backgroundColor={blue300}
                                             size={30}
                                             style={style}/>
                                    <a onClick={() => this.getChildDir(file.filepath)}> {file.filename} </a>
                                    <Button style={del}  onClick={()=>this.deleteDir(file.filename)}><span className="glyphicon glyphicon-remove" aria-hidden="true"></span> </Button>
                                    <Button style={del}  onClick={()=>this.getChildDir(file.filepath)}   ><span className="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> </Button>
                                    <Button style={del}  onClick={()=>this.shareFileData(file.filepath,file.filename)}  ><span className="glyphicon glyphicon-share" aria-hidden="true"></span> </Button>
                                    <Checkbox  checked={true} style={star} value={file.filepath} onChange={(e) => this.handleChange(e,file.filepath)} >Star</Checkbox>

                                </div>

                            ):
                            (<div><Avatar  className="file-opt" icon={<FileFolder />}
                                           color={orange200}
                                           backgroundColor={purple500}
                                           size={30}
                                           style={style}/>
                                <a  onClick={()=>this.download(file.filepath,file.filename)} >{file.filename}</a>

                                <Button style={del} onClick={()=>this.deleteDir(file.filename)}> <span className="glyphicon glyphicon-remove" aria-hidden="true"></span> </Button>
                                <Button style={del}  onClick={()=>this.download(file.filepath,file.filename)} ><span className="glyphicon glyphicon-download" aria-hidden="true"></span></Button>
                                <Button style={del}  onClick={()=>this.shareFileData(file.filepath,file.filename)} ><span className="glyphicon glyphicon-share" aria-hidden="true"></span> </Button>
                                <Checkbox  checked={true} style={star}  value={file.filepath} onChange={(e) => this.handleChange(e,file.filepath)} >Star</Checkbox>
                            </div> )}</ListGroupItem>
                    )}
                    </ListGroup>
                            </div>
                            <div style={{ display: (this.state.myfileShow ? 'block' : 'none') }}>
                    <div style={list}>
                        Your files
                    </div>
                    <ListGroup style={list}>
                        {filelist1.map((file, i) =>
                        <ListGroupItem style={fileListItems}  key={i}>{ !file.file  ?
                            (   <div>
                                    <Avatar  className="file-opt" icon={<FileFolder />}
                                             color={orange200}
                                             backgroundColor={blue300}
                                             size={30}
                                             style={style}/>
                                    <a onClick={() => this.getChildDir(file.filepath)}> {file.filename} </a>
                                    <Button style={del} onClick={()=>this.deleteDir(file.filename)}  ><span className="glyphicon glyphicon-remove" aria-hidden="true"></span></Button>
                                    <Button style={del}  onClick={()=>this.getChildDir(file.filepath)}  ><span className="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>  </Button>
                                    <Button style={del}  onClick={()=>this.shareFileData(file.filepath,file.filename)}   ><span className="glyphicon glyphicon-share" aria-hidden="true"></span></Button>
                                    <Checkbox  style={star}   checked={false} value={file.filepath} onChange={(e) => this.handleChange(e,file.filepath)} >Star</Checkbox>

                                </div>

                            ):
                            (<div><Avatar  className="file-opt" icon={<FileFolder />}
                                           color={orange200}
                                           backgroundColor={purple500}
                                           size={30}
                                           style={style}/>
                                <a  >{file.filename}</a>
                                <Button style={del} onClick={()=>this.deleteDir(file.filename)} ><span className="glyphicon glyphicon-remove" aria-hidden="true"></span></Button>
                                <Button style={del}  onClick={()=>this.download(file.filepath,file.filename)} ><span className="glyphicon glyphicon-download" aria-hidden="true"></span></Button>
                                <Button style={del}  onClick={()=>this.shareFileData(file.filepath,file.filename)}   ><span className="glyphicon glyphicon-share" aria-hidden="true"></span></Button>
                                <Checkbox  style={star} checked={false} value={file.filepath} onChange={(e) => this.handleChange(e,file.filepath)} >Star</Checkbox>
                            </div>)
                        }</ListGroupItem>
                    )}
                    </ListGroup>
                            </div>
                        </div>
                </div>
                </div>
            </div>

        )
    }
}

export default withRouter(Welcome);