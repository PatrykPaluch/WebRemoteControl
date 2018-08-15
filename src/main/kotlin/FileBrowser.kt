import java.io.File
import java.io.FilenameFilter

object FileBrowser {
    private val f = File(Settings.props.getProperty("lastfolder", "/"))
    private var currentFolder = if (f.exists() && f.isDirectory) f else File("/")
    var currentFiles = listOf<File>()

    private val filter = FilenameFilter { _: File, name: String ->
        val openExtensions = listOf(
                "3g2", "3gp", "3gp2", "3gpp", "amv", "asf", "asx", "avi", "bik", "bup", "div", "divx", "dv", "dvr", "dvr-ms", "evo", "f4v", "flv", "h264", "hdmov", "idx", "ifo", "ivf", "ivr", "k3g", "m1v", "m2p", "m2t", "m2ts", "m2v", "m4e", "m4v", "mkv", "mod", "mov", "mp2v", "mp4", "mp4v", "mpa", "mpe", "mpeg", "mpeg1", "mpeg2", "mpeg4", "mpg", "mps", "mpv", "mpv2", "mpv4", "mqv", "mswmm", "mts", "mxf", "nsv", "ogm", "ogv", "ogx", "pds", "pva", "qt", "rec", "rm", "rmvb", "roq", "rv", "smi", "smil", "smv", "srt", "sub", "swf", "tod", "tp", "tps", "trp", "ts", "tts", "vfw", "vob", "vp6", "vro", "webm", "wm", "wmv", "wtv", "xvid",
                "669", "aac", "aax", "ac3", "act", "adt", "adts", "aif", "aifc", "aiff", "amr", "aob", "ape", "au", "caf", "cda", "dts", "fla", "flac", "it", "kar", "m2a", "m4a", "m4b", "m4p", "m4r", "mid", "midi", "mka", "mp1", "mp2", "mp3", "mpc", "mpga", "nra", "oga", "ogg", "oma", "qcp", "ra", "ram", "rmi", "rmm", "s3m", "shn", "snd", "spx", "tta", "voc", "vqf", "w64", "wav", "wma", "wv", "xm", "xspf",
                "ashdisc", "b5t", "b6t", "bwt", "ccd", "cdi", "cue", "daa", "dmg", "dvd", "img", "iso", "isz", "mdf", "mds", "mdx", "nrg", "vcd", "xa"
        )
        var res = true
        if (name.startsWith("."))
            res = false
        else if (name.contains(".")) if (!openExtensions.contains(name.substring(name.lastIndexOf(".") + 1)))
            res = false
        res
    }

    fun updateFiles(folder: File? = null) {
        if (folder != null) {
            currentFolder = folder
            Settings.props["lastfolder"] = currentFolder.absolutePath
            Settings.save()
        }
        currentFiles = currentFolder.listFiles(filter).toList()
    }
    fun getFiles() = "fblist," + currentFiles.joinToString(",") { f -> f.name + (if (f.isDirectory) "/" else "")}
    fun goUp() = updateFiles(currentFolder.parentFile)

    init {
        updateFiles()
    }

}
