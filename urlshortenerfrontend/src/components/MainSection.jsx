import React, { useState } from 'react';
import RightSideImage from '../images/RightSideImage.webp'

const MainSection = () => {
    const [url, setUrl] = useState('');
    const handleShortener = () => {
        alert(`Shortened URL for: ${url}`)
    }

    return(
        <main className='main-section'>
            <div className='content'>
            <h1>A simple Link but a powerful tool for Youtubers</h1>
            <p>
                Our tool allows you to track your audience with simple, easy-to-remeber links.
            </p>
            <div className='input-group'>
                <input 
                    type="text"
                    placeholder="https://example.com"
                    value={url}
                    onChange={(e) => setUrl(e.target.value)}
                />
                <button onClick={handleShortener}>Try Now</button>
            </div>
            </div>
            <img src={RightSideImage} alt="" className='right-image'/>
        </main>
    )
}

export default MainSection;