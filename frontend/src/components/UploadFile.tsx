import { useCallback } from "react";
import { useDropzone } from "react-dropzone";
import { DEV_AUTH } from "../constants";
import axios from "axios";

type UploadFileProps = {
    accountName: string;
}

function UploadFile({ accountName }: UploadFileProps) {
    const onDrop = useCallback(async (acceptedFiles: any[]) => {
        const file = acceptedFiles[0];
        const formData = new FormData();
        formData.append('file', file);
        formData.append('account', accountName);

        try {
            const response = await axios.post('/api/upload', formData, {
                auth: DEV_AUTH,
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });

            if (response.status == 200) {
                alert('File uploaded and processed');
            }
        } catch (error) {
            console.error("Error uploading file:", error);
            alert('Upload failed.')
        }
    }, [accountName])

    const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop });
    return (
        <div {...getRootProps()} style={{
            border: '2px dashed #888',
            padding: '2rem',
            borderRadius: '8px',
            textAlign: 'center',
            backgroundColor: isDragActive ? '#f0f8ff' : 'white',
            cursor: 'pointer',
        }}>
            <input {...getInputProps()} />
            {
                isDragActive
                    ? <p>Drop the file here...</p>
                    : <p>Drag and drop a CSV file here, or click to upload</p>
            }
        </div>
    );
}

export default UploadFile;