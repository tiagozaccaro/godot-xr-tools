package org.godotengine.godot.io.file;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaStoreData.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0001\u0018\u0000 \u00112\u00020\u0001:\u0002\u0011\u0012B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/godotengine/godot/io/file/MediaStoreData;", "Lorg/godotengine/godot/io/file/DataAccess;", "context", "Landroid/content/Context;", "filePath", "", "accessFlag", "Lorg/godotengine/godot/io/file/FileAccessFlags;", "(Landroid/content/Context;Ljava/lang/String;Lorg/godotengine/godot/io/file/FileAccessFlags;)V", "fileChannel", "Ljava/nio/channels/FileChannel;", "getFileChannel", "()Ljava/nio/channels/FileChannel;", "id", "", "uri", "Landroid/net/Uri;", "Companion", "DataItem", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class MediaStoreData extends DataAccess {
    private static final String SELECTION_BY_ID = "_id = ? ";
    private static final String SELECTION_BY_PATH = "_display_name = ?  AND relative_path = ?";
    private final FileChannel fileChannel;
    private final long id;
    private final Uri uri;
    public static final Companion Companion = new Companion(null);
    private static final String TAG = MediaStoreData.class.getSimpleName();
    private static final Uri COLLECTION = MediaStore.Files.getContentUri("external_primary");
    private static final String[] PROJECTION = {"_id", "_display_name", "relative_path", "_size", "date_modified", "media_type"};

    /* compiled from: MediaStoreData.kt */
    @Metadata(k = 3, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FileAccessFlags.values().length];
            iArr[FileAccessFlags.READ.ordinal()] = 1;
            iArr[FileAccessFlags.WRITE.ordinal()] = 2;
            iArr[FileAccessFlags.READ_WRITE.ordinal()] = 3;
            iArr[FileAccessFlags.WRITE_READ.ordinal()] = 4;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaStoreData(Context context, String filePath, FileAccessFlags accessFlag) {
        super(filePath);
        DataItem dataItem;
        FileChannel channel;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(accessFlag, "accessFlag");
        ContentResolver contentResolver = context.getContentResolver();
        Companion companion = Companion;
        List dataItems = companion.queryByPath(context, filePath);
        switch (WhenMappings.$EnumSwitchMapping$0[accessFlag.ordinal()]) {
            case 1:
                if (dataItems.isEmpty()) {
                    throw new FileNotFoundException("Unable to access file " + filePath);
                }
                dataItem = (DataItem) dataItems.get(0);
                break;
            case 2:
            case 3:
            case 4:
                if (dataItems.isEmpty()) {
                    dataItem = companion.addFile(context, filePath);
                } else {
                    dataItem = (DataItem) dataItems.get(0);
                }
                if (dataItem == null) {
                    throw new FileNotFoundException("Unable to access file " + filePath);
                }
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        this.id = dataItem.getId();
        Uri uri = dataItem.getUri();
        this.uri = uri;
        ParcelFileDescriptor parcelFileDescriptor = contentResolver.openFileDescriptor(uri, accessFlag.getMode());
        if (parcelFileDescriptor == null) {
            throw new IllegalStateException("Unable to access file descriptor");
        }
        if (accessFlag == FileAccessFlags.READ) {
            channel = new FileInputStream(parcelFileDescriptor.getFileDescriptor()).getChannel();
            Intrinsics.checkNotNullExpressionValue(channel, "{\n\t\t\tFileInputStream(par…leDescriptor).channel\n\t\t}");
        } else {
            channel = new FileOutputStream(parcelFileDescriptor.getFileDescriptor()).getChannel();
            Intrinsics.checkNotNullExpressionValue(channel, "{\n\t\t\tFileOutputStream(pa…leDescriptor).channel\n\t\t}");
        }
        this.fileChannel = channel;
        if (!accessFlag.shouldTruncate()) {
            return;
        }
        getFileChannel().truncate(0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: MediaStoreData.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0082\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\n¢\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001d\u001a\u00020\nHÆ\u0003J\t\u0010\u001e\u001a\u00020\nHÆ\u0003J\t\u0010\u001f\u001a\u00020\nHÆ\u0003JO\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\nHÆ\u0001J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010$\u001a\u00020\nHÖ\u0001J\t\u0010%\u001a\u00020\u0007HÖ\u0001R\u0011\u0010\u000b\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\f\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000fR\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006&"}, d2 = {"Lorg/godotengine/godot/io/file/MediaStoreData$DataItem;", "", "id", "", "uri", "Landroid/net/Uri;", "displayName", "", "relativePath", "size", "", "dateModified", "mediaType", "(JLandroid/net/Uri;Ljava/lang/String;Ljava/lang/String;III)V", "getDateModified", "()I", "getDisplayName", "()Ljava/lang/String;", "getId", "()J", "getMediaType", "getRelativePath", "getSize", "getUri", "()Landroid/net/Uri;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class DataItem {
        private final int dateModified;
        private final String displayName;
        private final long id;
        private final int mediaType;
        private final String relativePath;
        private final int size;
        private final Uri uri;

        public final long component1() {
            return this.id;
        }

        public final Uri component2() {
            return this.uri;
        }

        public final String component3() {
            return this.displayName;
        }

        public final String component4() {
            return this.relativePath;
        }

        public final int component5() {
            return this.size;
        }

        public final int component6() {
            return this.dateModified;
        }

        public final int component7() {
            return this.mediaType;
        }

        public final DataItem copy(long j, Uri uri, String displayName, String relativePath, int i, int i2, int i3) {
            Intrinsics.checkNotNullParameter(uri, "uri");
            Intrinsics.checkNotNullParameter(displayName, "displayName");
            Intrinsics.checkNotNullParameter(relativePath, "relativePath");
            return new DataItem(j, uri, displayName, relativePath, i, i2, i3);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof DataItem) {
                DataItem dataItem = (DataItem) obj;
                return this.id == dataItem.id && Intrinsics.areEqual(this.uri, dataItem.uri) && Intrinsics.areEqual(this.displayName, dataItem.displayName) && Intrinsics.areEqual(this.relativePath, dataItem.relativePath) && this.size == dataItem.size && this.dateModified == dataItem.dateModified && this.mediaType == dataItem.mediaType;
            }
            return false;
        }

        public int hashCode() {
            return (((((((((((Long.hashCode(this.id) * 31) + this.uri.hashCode()) * 31) + this.displayName.hashCode()) * 31) + this.relativePath.hashCode()) * 31) + Integer.hashCode(this.size)) * 31) + Integer.hashCode(this.dateModified)) * 31) + Integer.hashCode(this.mediaType);
        }

        public String toString() {
            long j = this.id;
            Uri uri = this.uri;
            String str = this.displayName;
            String str2 = this.relativePath;
            int i = this.size;
            int i2 = this.dateModified;
            return "DataItem(id=" + j + ", uri=" + uri + ", displayName=" + str + ", relativePath=" + str2 + ", size=" + i + ", dateModified=" + i2 + ", mediaType=" + this.mediaType + ")";
        }

        public DataItem(long id, Uri uri, String displayName, String relativePath, int size, int dateModified, int mediaType) {
            Intrinsics.checkNotNullParameter(uri, "uri");
            Intrinsics.checkNotNullParameter(displayName, "displayName");
            Intrinsics.checkNotNullParameter(relativePath, "relativePath");
            this.id = id;
            this.uri = uri;
            this.displayName = displayName;
            this.relativePath = relativePath;
            this.size = size;
            this.dateModified = dateModified;
            this.mediaType = mediaType;
        }

        public final long getId() {
            return this.id;
        }

        public final Uri getUri() {
            return this.uri;
        }

        public final String getDisplayName() {
            return this.displayName;
        }

        public final String getRelativePath() {
            return this.relativePath;
        }

        public final int getSize() {
            return this.size;
        }

        public final int getDateModified() {
            return this.dateModified;
        }

        public final int getMediaType() {
            return this.mediaType;
        }
    }

    /* compiled from: MediaStoreData.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\r\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\bH\u0002J\u0018\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0002J\u0016\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\bJ\u0016\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\bJ\u0016\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\bJ\u0018\u0010\u001b\u001a\n \u0005*\u0004\u0018\u00010\b0\b2\u0006\u0010\u0011\u001a\u00020\bH\u0002J\u0010\u0010\u001c\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\bH\u0002J\u001b\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u001e\u001a\u00020\u001aH\u0002¢\u0006\u0002\u0010\u001fJ\u001b\u0010 \u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u0011\u001a\u00020\bH\u0002¢\u0006\u0002\u0010!J\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00132\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u001aH\u0002J\u001e\u0010#\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00132\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\bH\u0002J\u001e\u0010$\u001a\u00020\u00172\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010%\u001a\u00020\b2\u0006\u0010&\u001a\u00020\bR\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n \u0005*\u0004\u0018\u00010\b0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/godotengine/godot/io/file/MediaStoreData$Companion;", "", "()V", "COLLECTION", "Landroid/net/Uri;", "kotlin.jvm.PlatformType", "PROJECTION", "", "", "[Ljava/lang/String;", "SELECTION_BY_ID", "SELECTION_BY_PATH", "TAG", "addFile", "Lorg/godotengine/godot/io/file/MediaStoreData$DataItem;", "context", "Landroid/content/Context;", "path", "dataItemFromCursor", "", "query", "Landroid/database/Cursor;", "delete", "", "fileExists", "fileLastModified", "", "getMediaStoreDisplayName", "getMediaStoreRelativePath", "getSelectionByIdArgument", "id", "(J)[Ljava/lang/String;", "getSelectionByPathArguments", "(Ljava/lang/String;)[Ljava/lang/String;", "queryById", "queryByPath", "rename", "from", "to", "lib_templateDebug"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        private final String[] getSelectionByPathArguments(String path) {
            String mediaStoreDisplayName = getMediaStoreDisplayName(path);
            Intrinsics.checkNotNullExpressionValue(mediaStoreDisplayName, "getMediaStoreDisplayName(path)");
            return new String[]{mediaStoreDisplayName, getMediaStoreRelativePath(path)};
        }

        private final String[] getSelectionByIdArgument(long id) {
            return new String[]{String.valueOf(id)};
        }

        private final String getMediaStoreDisplayName(String path) {
            return new File(path).getName();
        }

        /* JADX WARN: Code restructure failed: missing block: B:5:0x0021, code lost:
            if (r2 == null) goto L11;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private final java.lang.String getMediaStoreRelativePath(java.lang.String r9) {
            /*
                r8 = this;
                java.io.File r0 = new java.io.File
                r0.<init>(r9)
                java.io.File r1 = android.os.Environment.getExternalStorageDirectory()
                java.lang.String r2 = r0.getParent()
                if (r2 == 0) goto L23
                java.lang.String r3 = r1.getAbsolutePath()
                java.lang.String r4 = "environmentDir.absolutePath"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r4)
                java.lang.String r4 = ""
                r5 = 0
                r6 = 4
                r7 = 0
                java.lang.String r2 = kotlin.text.StringsKt.replace$default(r2, r3, r4, r5, r6, r7)
                if (r2 != 0) goto L25
            L23:
                java.lang.String r2 = ""
            L25:
                r3 = 1
                char[] r4 = new char[r3]
                r5 = 0
                r6 = 47
                r4[r5] = r6
                java.lang.String r2 = kotlin.text.StringsKt.trim(r2, r4)
                r4 = r2
                java.lang.CharSequence r4 = (java.lang.CharSequence) r4
                boolean r4 = kotlin.text.StringsKt.isBlank(r4)
                r3 = r3 ^ r4
                if (r3 == 0) goto L4e
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.StringBuilder r3 = r3.append(r2)
                java.lang.String r4 = "/"
                java.lang.StringBuilder r3 = r3.append(r4)
                java.lang.String r2 = r3.toString()
            L4e:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: org.godotengine.godot.io.file.MediaStoreData.Companion.getMediaStoreRelativePath(java.lang.String):java.lang.String");
        }

        private final List<DataItem> queryById(Context context, long id) {
            Cursor query = context.getContentResolver().query(MediaStoreData.COLLECTION, MediaStoreData.PROJECTION, MediaStoreData.SELECTION_BY_ID, getSelectionByIdArgument(id), null);
            return dataItemFromCursor(query);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<DataItem> queryByPath(Context context, String path) {
            Cursor query = context.getContentResolver().query(MediaStoreData.COLLECTION, MediaStoreData.PROJECTION, MediaStoreData.SELECTION_BY_PATH, getSelectionByPathArguments(path), null);
            return dataItemFromCursor(query);
        }

        private final List<DataItem> dataItemFromCursor(Cursor query) {
            if (query != null) {
                Cursor cursor = query;
                try {
                    Cursor cursor2 = cursor;
                    boolean z = false;
                    cursor2.getCount();
                    if (cursor2.getCount() == 0) {
                        List<DataItem> emptyList = CollectionsKt.emptyList();
                        CloseableKt.closeFinally(cursor, null);
                        return emptyList;
                    }
                    int idColumn = cursor2.getColumnIndexOrThrow("_id");
                    int displayNameColumn = cursor2.getColumnIndexOrThrow("_display_name");
                    int relativePathColumn = cursor2.getColumnIndexOrThrow("relative_path");
                    int sizeColumn = cursor2.getColumnIndexOrThrow("_size");
                    int dateModifiedColumn = cursor2.getColumnIndexOrThrow("date_modified");
                    int mediaTypeColumn = cursor2.getColumnIndexOrThrow("media_type");
                    ArrayList result = new ArrayList();
                    while (cursor2.moveToNext()) {
                        long id = cursor2.getLong(idColumn);
                        Uri withAppendedId = ContentUris.withAppendedId(MediaStoreData.COLLECTION, id);
                        Intrinsics.checkNotNullExpressionValue(withAppendedId, "withAppendedId(COLLECTION, id)");
                        String string = cursor2.getString(displayNameColumn);
                        Intrinsics.checkNotNullExpressionValue(string, "cursor.getString(displayNameColumn)");
                        String string2 = cursor2.getString(relativePathColumn);
                        Intrinsics.checkNotNullExpressionValue(string2, "cursor.getString(relativePathColumn)");
                        result.add(new DataItem(id, withAppendedId, string, string2, cursor2.getInt(sizeColumn), cursor2.getInt(dateModifiedColumn), cursor2.getInt(mediaTypeColumn)));
                        z = z;
                        cursor2 = cursor2;
                    }
                    ArrayList arrayList = result;
                    CloseableKt.closeFinally(cursor, null);
                    return arrayList;
                } finally {
                }
            } else {
                return CollectionsKt.emptyList();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final DataItem addFile(Context context, String path) {
            ContentValues fileDetails = new ContentValues();
            fileDetails.put("_id", (Integer) 0);
            fileDetails.put("_display_name", MediaStoreData.Companion.getMediaStoreDisplayName(path));
            fileDetails.put("relative_path", MediaStoreData.Companion.getMediaStoreRelativePath(path));
            if (context.getContentResolver().insert(MediaStoreData.COLLECTION, fileDetails) == null) {
                return null;
            }
            List infos = queryByPath(context, path);
            if (infos.isEmpty()) {
                return null;
            }
            return infos.get(0);
        }

        public final boolean delete(Context context, String path) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(path, "path");
            List itemsToDelete = queryByPath(context, path);
            if (itemsToDelete.isEmpty()) {
                return false;
            }
            ContentResolver resolver = context.getContentResolver();
            int itemsDeleted = 0;
            for (DataItem item : itemsToDelete) {
                itemsDeleted += resolver.delete(item.getUri(), null, null);
            }
            return itemsDeleted > 0;
        }

        public final boolean fileExists(Context context, String path) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(path, "path");
            return !queryByPath(context, path).isEmpty();
        }

        public final long fileLastModified(Context context, String path) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(path, "path");
            List result = queryByPath(context, path);
            if (result.isEmpty()) {
                return 0L;
            }
            DataItem dataItem = result.get(0);
            return dataItem.getDateModified();
        }

        public final boolean rename(Context context, String from, String to) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(from, "from");
            Intrinsics.checkNotNullParameter(to, "to");
            List sources = queryByPath(context, from);
            if (sources.isEmpty()) {
                return false;
            }
            DataItem source = sources.get(0);
            ContentValues updatedDetails = new ContentValues();
            updatedDetails.put("_display_name", MediaStoreData.Companion.getMediaStoreDisplayName(to));
            updatedDetails.put("relative_path", MediaStoreData.Companion.getMediaStoreRelativePath(to));
            int updated = context.getContentResolver().update(source.getUri(), updatedDetails, MediaStoreData.SELECTION_BY_ID, getSelectionByIdArgument(source.getId()));
            return updated > 0;
        }
    }

    @Override // org.godotengine.godot.io.file.DataAccess
    protected FileChannel getFileChannel() {
        return this.fileChannel;
    }
}