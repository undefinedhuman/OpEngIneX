#version 410 core

in vec2 textureCoords;
in vec4 positionInCameraSpace;
in vec4 positionInClipSpace;
in vec3 toCameraRay;

out vec4 fragColor;

struct DirectionalLight {
    float intensity;
    vec3 direction;
    vec3 color;
};

uniform DirectionalLight sun;

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;
uniform sampler2D dudvTexture;
uniform sampler2D normalTexture;
uniform sampler2D depthTexture;

uniform float waveFactor;
uniform float specularStrength;
uniform float shineDamper;

uniform float fogDensity;
uniform float fogPower;
uniform vec3 fogColor;

const float zNear = 0.1;
const float zFar = 1000.0;
const vec4 WATER_COLOR = vec4(0, 0.3, 0.5, 1);
const vec4 DEEP_WATER_COLOR = vec4(0, 0.1, 0.2f, 1);
const float WAVE_STRENGTH = 0.02;

vec3 calcSpecularLight(DirectionalLight light, vec3 unitCamera, vec3 unitNormal) {
    vec3 reflectedLight = normalize(reflect(-normalize(light.direction), unitNormal));
    float specularFactor = pow(max(dot(unitCamera, reflectedLight), 0.0), shineDamper);
    return specularStrength * light.intensity * specularFactor * light.color;
}

float linearDepth(float depthColorFromSampler) {
    return 2.0 * zNear * zFar / (zFar + zNear - (2.0 * depthColorFromSampler - 1.0) * (zFar - zNear));
}

void main() {
    vec2 positionInDeviceTextureCoords = (positionInClipSpace.xy/positionInClipSpace.w)/2.0 + 0.5f;

    float distanceToWaterFloor = linearDepth(texture(depthTexture, positionInDeviceTextureCoords).r);
    float distanceToWaterQuad = linearDepth(gl_FragCoord.z);
    float waterDepth = distanceToWaterFloor - distanceToWaterQuad;

    vec2 distortedTextureCoords = texture(dudvTexture, vec2(textureCoords.x + waveFactor, textureCoords.y)).rg * 0.1;
    distortedTextureCoords = textureCoords + vec2(distortedTextureCoords.x, distortedTextureCoords.y + waveFactor);
    vec2 distortion = (texture(dudvTexture, distortedTextureCoords).rg * 2.0 - 1.0) * WAVE_STRENGTH * clamp(waterDepth/20, 0, 1);

    vec4 reflectionColor = texture(reflectionTexture, vec2(clamp(positionInDeviceTextureCoords.x + distortion.x, 0.002, 0.998), clamp(-positionInDeviceTextureCoords.y + distortion.y, -0.998, -0.002)));
    vec4 refractionColor = texture(refractionTexture, clamp(positionInDeviceTextureCoords + distortion, 0.002, 0.998));
    refractionColor = mix(refractionColor, DEEP_WATER_COLOR, clamp(waterDepth/60.0, 0.0, 1.0));

    vec3 unitCamera = normalize(toCameraRay);

    vec4 normalColor = texture(normalTexture, distortedTextureCoords);
    vec3 normal = normalize(vec3(normalColor.r * 2.0 - 1.0, normalColor.b * 3.67f, normalColor.g * 2.0 - 1.0));
    vec3 specular = calcSpecularLight(sun, unitCamera, normal) * clamp(waterDepth/5, 0, 1);

    float fogFactor = clamp(exp(-pow((length(positionInCameraSpace.xyz) * fogDensity), fogPower)), 0, 1);

    fragColor = mix(reflectionColor, refractionColor, pow(dot(unitCamera, vec3(0, 1, 0)), 0.5));
    fragColor = mix(fragColor, WATER_COLOR, 0.2) + vec4(specular, 0.0);
    fragColor.a = clamp(waterDepth/5, 0, 1);
    fragColor = mix(vec4(fogColor, 1), fragColor, fogFactor);
}
